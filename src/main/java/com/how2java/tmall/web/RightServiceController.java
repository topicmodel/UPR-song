package com.how2java.tmall.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.how2java.tmall.pojo.PatentDomain;
import com.how2java.tmall.pojo.RightService;
import com.how2java.tmall.service.PatentDomainService;
import com.how2java.tmall.service.RightServiceService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page4Navigator;

@RestController
public class RightServiceController {

	@Autowired
	PatentDomainService patentDomainService;
	@Autowired
	RightServiceService rightServiceService;

	// 获取知产服务领域列表
	@GetMapping("/domains")
	public Page4Navigator<PatentDomain> list(@RequestParam(name = "start", defaultValue = "0") int start,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		start = start < 0 ? 0 : start;
		Page4Navigator<PatentDomain> page = patentDomainService.list(start, size, 5);
		return page;
	}

	// 增加知产服务分类
	@PostMapping("/domains")
	public Object add(PatentDomain bean, HttpServletRequest request, MultipartFile image) throws IOException {
		patentDomainService.add(bean);
		saveOrUpdateImageFile(bean, request, image);
		return bean;
	}
	
	//编辑知产服务分类
	@GetMapping("/servicecategories/{id}")
	public Object get(@PathVariable("id") int id){
		PatentDomain bean = patentDomainService.get(id);
		return bean;
	}
	//修改知产服务分类
	@PutMapping("/servicecategories/{id}")
	public Object updateServiceCategory(
		PatentDomain bean,MultipartFile image,HttpServletRequest request) throws IOException{
		String domainName = request.getParameter("domainName");
		bean.setDomainName(domainName);
		patentDomainService.update(bean);
		if(image != null){			
			saveOrUpdateImageFile(bean, request, image);
		}
		return bean;
	}
	
	private void saveOrUpdateImageFile(PatentDomain bean, HttpServletRequest request, MultipartFile image)
			throws IOException {
		File imageFolder = new File(request.getServletContext().getRealPath("img/service"));
		File file = new File(imageFolder, bean.getId() + ".jpg");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		image.transferTo(file);
		BufferedImage img = ImageUtil.change2jpg(file);
		ImageIO.write(img, "jpg", file);
	}

	// 删除知产服务分类
	@DeleteMapping("/domains/{id}")
	public Object delete(@PathVariable(name = "id") int id, HttpServletRequest request) {
		patentDomainService.delete(id);
		File imageFolder = new File(request.getServletContext().getRealPath("img/service"));
		File file = new File(imageFolder, id + ".jpg");
		file.delete();
		return null;
	}
	
	//根据领域id查询知产服务领域
	@GetMapping("/domains/{pdid}")
	public Object getDomain(@PathVariable("pdid") int pdid){
		PatentDomain bean = patentDomainService.get(pdid);
		return bean;
	}
	
	//查询领域分类下的知产服务
	@GetMapping("domains/{pdid}/serviceproducts")
	public Object getRightService(
		@PathVariable("pdid") int pdid,
		@RequestParam(value="start",defaultValue="0") int start,
		@RequestParam(value="size",defaultValue="5") int size){
		start = start<0?0:start;
		Page4Navigator<RightService> page = rightServiceService.list(pdid, start, size, 5);
		return page;
	}	
	//增加服务
	@PostMapping("/serviceproducts")
	public Object addServiceProduct(@RequestBody RightService bean){
		bean.setCreateDate(new Date());
		rightServiceService.add(bean);
		return  bean;
	}
	//删除指定领域下的分类
	@DeleteMapping("/serviceproducts/{id}")
	public String deleteServiceProduct(@PathVariable("id") int id){
		rightServiceService.delete(id);
		return null;
	}
	
	@GetMapping("/rightservices/{rsid}")
	public Object getRightServiceById(@PathVariable("rsid") int rsid){
		RightService bean = rightServiceService.get(rsid);
		return bean;
	}
}
