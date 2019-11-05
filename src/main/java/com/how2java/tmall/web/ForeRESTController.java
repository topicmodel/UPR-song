package com.how2java.tmall.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.how2java.tmall.comparator.ProductAllComparator;
import com.how2java.tmall.comparator.ProductDateComparator;
import com.how2java.tmall.comparator.ProductPriceComparator;
import com.how2java.tmall.comparator.ProductReviewComparator;
import com.how2java.tmall.comparator.ProductSaleCountComparator;
import com.how2java.tmall.pojo.Agency;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Demand;
import com.how2java.tmall.pojo.InfoCategory;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.PatentDomain;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.RightService;
import com.how2java.tmall.pojo.RightServiceImage;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.AgencyService;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.DemandService;
import com.how2java.tmall.service.InfoCategoryService;
import com.how2java.tmall.service.InformationService;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.PatentDomainService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.PropertyValueService;
import com.how2java.tmall.service.ReviewService;
import com.how2java.tmall.service.RightServiceImageService;
import com.how2java.tmall.service.RightServiceService;
import com.how2java.tmall.service.UserService;
import com.how2java.tmall.util.Page4Navigator;
import com.how2java.tmall.util.Result;

@RestController
public class ForeRESTController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;
	@Autowired
	ProductImageService productImageService;
	@Autowired
	PropertyValueService propertyValueService;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	OrderService orderService;
	@Autowired
	DemandService demandService;
	@Autowired
	AgencyService agencyService;
	@Autowired
	PatentDomainService patentDomainService;
	@Autowired
	RightServiceService rightServiceService;
	@Autowired
	RightServiceImageService rightServiceImageService;
	@Autowired
	InfoCategoryService infoCategoryService;
	@Autowired
	InformationService informationService;
	
	//知识产权交易首页信息
	@GetMapping("/forehome")
	public Object home() {
		List<Category> cs = categoryService.list();
		productService.fill(cs);
		productService.fillByRow(cs);
		categoryService.removeCategoryFromProduct(cs);
		return cs;
	}
	
	//首页信息展示
	@GetMapping("/foreinfodisplay")
	public Object infodisplay() {
		List<InfoCategory> ics = infoCategoryService.list();
		informationService.fill(ics);
		//informationService.fillByRow(ics);
		infoCategoryService.removeInfoCategoryFromInformation(ics);
		return ics;
	}
	
	@PostMapping("/foreregister")
	public Object register(@RequestBody User user) {
		System.err.println(user.toString());
		String name = user.getName();
		String password = user.getPassword();
		name = HtmlUtils.htmlEscape(name);
		user.setName(name);
		boolean exist = userService.isExist(name);

		if (exist) {
			String message = "用户名已经被使用,不能使用";
			return Result.fail(message);
		}

		user.setPassword(password);

		userService.add(user);

		return Result.success();
	}

	@PostMapping("/forelogin")
	public Object login(@RequestBody User userParam, HttpSession session) {
		String name = userParam.getName();
		name = HtmlUtils.htmlEscape(name);

		User user = userService.get(name, userParam.getPassword());
		if (null == user) {
			String message = "账号密码错误";
			return Result.fail(message);
		} else {
			session.setAttribute("user", user);
			return Result.success();
		}
	}

	@GetMapping("/foreproduct/{pid}")
	public Object product(@PathVariable("pid") int pid) {
		Product product = productService.get(pid);

		List<ProductImage> productSingleImages = productImageService.listSingleProductImages(product);
		List<ProductImage> productDetailImages = productImageService.listDetailProductImages(product);
		product.setProductSingleImages(productSingleImages);
		product.setProductDetailImages(productDetailImages);

		List<PropertyValue> pvs = propertyValueService.list(product);
		List<Review> reviews = reviewService.list(product);
		productService.setSaleAndReviewNumber(product);
		productImageService.setFirstProdutImage(product);

		Map<String, Object> map = new HashMap<>();
		map.put("product", product);
		map.put("pvs", pvs);
		map.put("reviews", reviews);

		return Result.success(map);
	}

	@GetMapping("forecheckLogin")
	public Object checkLogin(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (null != user)
			return Result.success();
		return Result.fail("未登录");
	}

	@GetMapping("forecategory/{cid}")
	public Object category(@PathVariable int cid, String sort) {
		Category c = categoryService.get(cid);
		productService.fill(c);
		productService.setSaleAndReviewNumber(c.getProducts());
		categoryService.removeCategoryFromProduct(c);

		if (null != sort) {
			switch (sort) {
			case "review":
				Collections.sort(c.getProducts(), new ProductReviewComparator());
				break;
			case "date":
				Collections.sort(c.getProducts(), new ProductDateComparator());
				break;

			case "saleCount":
				Collections.sort(c.getProducts(), new ProductSaleCountComparator());
				break;

			case "price":
				Collections.sort(c.getProducts(), new ProductPriceComparator());
				break;

			case "all":
				Collections.sort(c.getProducts(), new ProductAllComparator());
				break;
			}
		}

		return c;
	}


	@GetMapping("forebuyone")
	public Object buyone(int pid, int num, HttpSession session) {
		System.out.println("user:" + session.getAttribute("user").toString());
		return buyoneAndAddCart(pid, num, session);
	}

	private int buyoneAndAddCart(int pid, int num, HttpSession session) {
		Product product = productService.get(pid);
		System.err.println("product:" + product.toString());
		int oiid = 0;

		User user = (User) session.getAttribute("user");
		boolean found = false;
		List<OrderItem> ois = orderItemService.listByUser(user);
		for (OrderItem oi : ois) {
			if (oi.getProduct().getId() == product.getId()) {
				oi.setNumber(num);
				orderItemService.update(oi);
				found = true;
				oiid = oi.getId();
				break;
			}
		}

		if (!found) {
			OrderItem oi = new OrderItem();
			oi.setUser(user);
			oi.setProduct(product);
			oi.setNumber(num);
			orderItemService.add(oi);
			oiid = oi.getId();
		}
		return oiid;
	}

	@GetMapping("forebuy")
	public Object buy(String[] oiid, HttpSession session) {
		List<OrderItem> orderItems = new ArrayList<>();
		float total = 0;

		for (String strid : oiid) {
			int id = Integer.parseInt(strid);
			OrderItem oi = orderItemService.get(id);
			total += oi.getProduct().getPromotePrice() * oi.getNumber();
			orderItems.add(oi);
		}

		productImageService.setFirstProdutImagesOnOrderItems(orderItems);

		session.setAttribute("ois", orderItems);

		Map<String, Object> map = new HashMap<>();
		map.put("orderItems", orderItems);
		map.put("total", total);
		return Result.success(map);
	}

	@GetMapping("foreaddCart")
	public Object addCart(int pid, int num, HttpSession session) {
		buyoneAndAddCart(pid, num, session);
		return Result.success();
	}

	@GetMapping("forecart")
	public Object cart(HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<OrderItem> ois = orderItemService.listByUser(user);
		productImageService.setFirstProdutImagesOnOrderItems(ois);
		return ois;
	}

	@GetMapping("forechangeOrderItem")
	public Object changeOrderItem(HttpSession session, int pid, int num) {
		User user = (User) session.getAttribute("user");
		if (null == user)
			return Result.fail("未登录");

		List<OrderItem> ois = orderItemService.listByUser(user);
		for (OrderItem oi : ois) {
			if (oi.getProduct().getId() == pid) {
				oi.setNumber(num);
				orderItemService.update(oi);
				break;
			}
		}
		return Result.success();
	}

	@GetMapping("foredeleteOrderItem")
	public Object deleteOrderItem(HttpSession session, int oiid) {
		User user = (User) session.getAttribute("user");
		if (null == user)
			return Result.fail("未登录");
		orderItemService.delete(oiid);
		return Result.success();
	}

	@PostMapping("forecreateOrder")
	public Object createOrder(@RequestBody Order order, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (null == user)
			return Result.fail("未登录");
		String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
		order.setOrderCode(orderCode);
		order.setCreateDate(new Date());
		order.setUser(user);
		order.setStatus(OrderService.waitPay);
		List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");

		float total = orderService.add(order, ois);

		Map<String, Object> map = new HashMap<>();
		map.put("oid", order.getId());
		map.put("total", total);

		return Result.success(map);
	}

	@GetMapping("forepayed")
	public Object payed(int oid) {
		Order order = orderService.get(oid);
		order.setStatus(OrderService.waitDelivery);
		order.setPayDate(new Date());
		orderService.update(order);
		return order;
	}

	@GetMapping("forebought")
	public Object bought(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (null == user)
			return Result.fail("未登录");
		List<Order> os = orderService.listByUserWithoutDelete(user);
		orderService.removeOrderFromOrderItem(os);
		return os;
	}

	@GetMapping("foreconfirmPay")
	public Object confirmPay(int oid) {
		Order o = orderService.get(oid);
		orderItemService.fill(o);
		orderService.cacl(o);
		orderService.removeOrderFromOrderItem(o);
		return o;
	}

	@GetMapping("foreorderConfirmed")
	public Object orderConfirmed(int oid) {
		Order o = orderService.get(oid);
		o.setStatus(OrderService.waitReview);
		o.setConfirmDate(new Date());
		orderService.update(o);
		return Result.success();
	}

	@PutMapping("foredeleteOrder")
	public Object deleteOrder(int oid) {
		Order o = orderService.get(oid);
		o.setStatus(OrderService.delete);
		orderService.update(o);
		return Result.success();
	}

	@GetMapping("forereview")
	public Object review(int oid) {
		Order o = orderService.get(oid);
		orderItemService.fill(o);
		orderService.removeOrderFromOrderItem(o);
		Product p = o.getOrderItems().get(0).getProduct();
		List<Review> reviews = reviewService.list(p);
		productService.setSaleAndReviewNumber(p);
		Map<String, Object> map = new HashMap<>();
		map.put("p", p);
		map.put("o", o);
		map.put("reviews", reviews);

		return Result.success(map);
	}

	@PostMapping("foredoreview")
	public Object doreview(HttpSession session, int oid, int pid, String content) {
		Order o = orderService.get(oid);
		o.setStatus(OrderService.finish);
		orderService.update(o);

		Product p = productService.get(pid);
		content = HtmlUtils.htmlEscape(content);

		User user = (User) session.getAttribute("user");
		Review review = new Review();
		review.setContent(content);
		review.setProduct(p);
		review.setCreateDate(new Date());
		review.setUser(user);
		reviewService.add(review);
		return Result.success();
	}

	// 获取需求列表
	@GetMapping("/foredemand")
	public Object demand(@RequestParam(name = "start", defaultValue = "0") int start,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		Page4Navigator<Demand> page = demandService.findAll(start, size, 5);
		return page;
	}

	// 获取代理公司列表
	@GetMapping("/foreagency")
	public Object agency(@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		Page4Navigator<Agency> page = agencyService.list(start, size, 5);
		return page;
	}

	// 获取高分专利列表
	@GetMapping("/highScoreProductList")
	public Object highScoreProductList() {
		List<Category> cs = categoryService.list();
		productService.specialFill(cs);
		categoryService.removeCategoryFromProduct(cs);
		return cs;
	}

	// 根据id获取需求详情
	@GetMapping("/foredemanddesc/{did}")
	public Object getDemandDesc(@PathVariable("did") int did) {
		Demand demand = demandService.get(did);
		System.err.println("demand:"+demand.toString());
		return demand;
	}
	//获取数达自营的知产服务列表
	@GetMapping("/foreshudashop")
	public Object shudaShop(){
		List<PatentDomain> domains = patentDomainService.list();
		rightServiceService.fill(domains);
		patentDomainService.removePatentDomainFromRigtService(domains);
		return domains;
	}
	//获取知产服务详情
	@GetMapping("/forerightservice/{rsid}")
	public Object rightServiceDetail(@PathVariable("rsid") int rsid){
		RightService rightService = rightServiceService.get(rsid);
		List<RightServiceImage> rightServiceImages = rightServiceImageService.listSingleRightServiceImages(rightService);
		rightService.setRigthServiceSingleImages(rightServiceImages);
		rightServiceImageService.setFirstRightServiceImage(rightService);
		Map<String,Object> map = new HashMap<>();
		map.put("service", rightService);
		return Result.success(map);
	}
}
