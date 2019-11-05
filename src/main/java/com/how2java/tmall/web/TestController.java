package com.how2java.tmall.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.how2java.tmall.pojo.ProductAssess;
import com.how2java.tmall.service.ProductAssessService;

@Controller
public class TestController {
	
	@Autowired
	ProductAssessService productAssessService;

	@RequestMapping("/viewAssess")
	public String listViewEcharts(){
		return "test/viewAssess";
	}
	
	@RequestMapping("/getAssess")
	@ResponseBody
	public ProductAssess getAssess(@RequestParam("pid") int pid){
		ProductAssess bean =  productAssessService.get(pid);
		return bean;
	}
}
