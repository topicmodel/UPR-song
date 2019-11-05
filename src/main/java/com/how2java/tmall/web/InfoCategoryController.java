package com.how2java.tmall.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.how2java.tmall.pojo.InfoCategory;
import com.how2java.tmall.pojo.Information;
import com.how2java.tmall.service.InfoCategoryService;
import com.how2java.tmall.service.InformationService;
import com.how2java.tmall.util.Page4Navigator;

@RestController
public class InfoCategoryController {
	@Autowired
	InfoCategoryService infoCategoryService;
	/**
	 * 获取内容分类列表
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/infocategories")
	public Page4Navigator<InfoCategory> list(@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		start = start < 0 ? 0 : start;
		Page4Navigator<InfoCategory> page = infoCategoryService.list(start, size, 5);
		return page;
	}
	/**
	 * 根据内容分类id获取对应内容分类
	 * @param infoid
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/infocategories/{infocid}")
	public InfoCategory get(@PathVariable("infocid") int infocid) throws Exception {
		InfoCategory bean = infoCategoryService.get(infocid);
		return bean;
	}
	
}
