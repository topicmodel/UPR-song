/**
 * 信息控制器类
 */
package com.how2java.tmall.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.how2java.tmall.pojo.Information;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.InfoCategoryService;
import com.how2java.tmall.service.InformationService;
import com.how2java.tmall.util.Page4Navigator;

@RestController
public class InformationController {
	@Autowired
	InformationService informationService;
	@Autowired
	InfoCategoryService infoCategoryService;

	@GetMapping("/infocategories/{infocid}/informations")
	public Page4Navigator<Information> list(@PathVariable("infocid") int infocid,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		start = start < 0 ? 0 : start;
		Page4Navigator<Information> page = informationService.list(infocid, start, size, 5);
		return page;
	}
	/**
	 * 根据信息id获取信息详情
	 * @param id
	 * @return
	 */
	@GetMapping("/informations/{infoid}")
	public Information get(@PathVariable("infoid") int id){
		Information bean = informationService.get(id);
		return bean;
	}
	
	@PostMapping("/informations")
	public Object add(@RequestBody Information bean) throws Exception {
		bean.setCreateDate(new Date());
		informationService.add(bean);
		return bean;
	}

	@DeleteMapping("/informations/{id}")
	public Object delete(@PathVariable("id") int id, HttpServletRequest request) {
		informationService.delete(id);
		return null;
	}

}
