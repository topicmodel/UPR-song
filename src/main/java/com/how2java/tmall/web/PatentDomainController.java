package com.how2java.tmall.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.how2java.tmall.pojo.PatentDomain;
import com.how2java.tmall.service.PatentDomainService;

/**
 * 相当于专利经纪人领域的大类筛选，一共八个大类
 * 
 * @author songkai
 *
 */
@RestController
public class PatentDomainController {

	@Autowired
	PatentDomainService patentDomainService;

	@GetMapping("/forepatentdomain")
	public Object list() {
		List<PatentDomain> beans = patentDomainService.list();
		System.err.println(beans.size());
		return beans;
	}
}
