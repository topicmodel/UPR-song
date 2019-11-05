package com.how2java.tmall.web;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Demand;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.DemandService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.UserService;
import com.how2java.tmall.util.Page4Navigator;
import com.how2java.tmall.util.SimilarityUtil;

@RestController
public class PersonalRESTController {

	@Autowired
	UserService userService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	DemandService demandService;
	
	
	// 展示个人信息
	@GetMapping("/personal")
	public User list(HttpSession session) {
		User user = (User) session.getAttribute("user");
		System.out.println("user:" + user);
		user = userService.getById(user.getId());
		return user;
	}

	// 编辑个人信息
	@GetMapping("/personal/{id}")
	public User edit(@PathVariable("id") int id) {
		User user = userService.getById(id);
		return user;
	}

	// 修改个人信息
	@PutMapping("/personal/{id}")
	public Object update(@RequestBody User bean, HttpServletRequest request) {
		userService.update(bean);
		return bean;
	}

	// 个人中心展示分类
	@GetMapping("/personalcategories")
	public Page4Navigator<Category> listCategory(@RequestParam(name = "start", defaultValue = "0") int start,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		start = start < 0 ? 0 : start;
		Page4Navigator<Category> page = categoryService.list(start, size, 5);
		System.err.println("用户中心显示的分类：" + page.getContent());
		return page;
	}

	@GetMapping("/personalcategories/{cid}/personalproducts")
	public Page4Navigator<Product> listProduct(HttpSession session, @PathVariable("cid") int cid,
			@RequestParam(name = "start", defaultValue = "0") int start,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		System.err.println("获取分类下商品!!!");
		User user = (User) session.getAttribute("user");
		start = start < 0 ? 0 : start;
		Page4Navigator<Product> page = productService.list(cid, user.getId(), start, size, 5);
		System.err.println("商品列表:" + page.getContent());
		return page;
	}

	@GetMapping("/personalcategories/{cid}")
	public Category getCategory(@PathVariable("cid") int cid) {
		Category bean = categoryService.get(cid);
		return bean;
	}

	// 增加产品
	@PostMapping("/personalproducts")
	public Object add(@RequestBody Product bean, HttpSession session) {
		User user = (User) session.getAttribute("user");
		bean.setUser(user);
		bean.setCreateDate(new Date());
		productService.add(bean);
		return bean;
	}
	
	//查询个人需求列表
	@GetMapping("/personaldemands")
	public Page4Navigator<Demand> listProduct(
			HttpSession session, 
			@RequestParam(name = "start", defaultValue = "0") int start,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		System.err.println("获取用户的需求!!!");
		User user = (User) session.getAttribute("user");
		System.err.println("user:"+user.toString());
		start = start < 0 ? 0 : start;
		Page4Navigator<Demand> page = demandService.list(user.getId(), start, size, 5);
		System.err.println("需求列表:" + page.getContent());
		return page;
	}
	
	//增加个人需求
	@PostMapping("/personaldemands")
	public Object add(
			@RequestBody Demand bean,
			HttpSession session){
		System.err.println("增加一条需求！！！");
		User user = (User) session.getAttribute("user");
		System.err.println(user.toString());
		bean.setUser(user);
		bean.setCreateDate(new Date());
		demandService.add(bean);
		return bean;
	}
	//删除个人需求
	@DeleteMapping("/personaldemands/{id}")
	public Object delete(@PathVariable("id") int id){
		demandService.delete(id);
		return null;
	}

	//计算需求匹配
	@GetMapping("/personaldemand/{id}/patentmatch")
	public Page4Navigator<Product> listMatchPatent(
			@RequestParam(name = "start", defaultValue = "0") int start,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@PathVariable("id") int id) {
		System.out.println("计算匹配度！！！");
		Demand demand = demandService.get(id);
		System.err.println("demand："+demand.getDemandContent());
		List<Product> products = productService.findAll();
		for(Product product : products){
			DecimalFormat df = new DecimalFormat("##.##");
			float matchScore = Float.parseFloat((df.format(SimilarityUtil.getSimilarity(demand.getDemandContent(), product.getPatentDesc())*100)));
			System.err.println("matchScore:"+matchScore);
			product.setMatchScore(matchScore);
			productService.update(product);
		}
		Page4Navigator<Product> page = productService.list(start, size, 2);
		System.err.println(page.getContent());;
		return page;
	}
}
