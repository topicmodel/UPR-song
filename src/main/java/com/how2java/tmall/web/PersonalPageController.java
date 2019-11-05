package com.how2java.tmall.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.how2java.tmall.pojo.User;

@Controller
public class PersonalPageController {
	
	@RequestMapping(value="/personal_info_list")
	public String listInfo(HttpSession session){
		User user =(User)session.getAttribute("user");
		 if(null==user){
			 return "fore/login";			 
		 }
		 session.setAttribute("user", user);
		return "personal/listInfo";
	}
	
	@GetMapping(value="/personal_info_edit")
    public String editCategory(HttpSession session){
		User user =(User)session.getAttribute("user");
		 if(null==user){
			 return "fore/login";			 
		 }
		 session.setAttribute("user", user);
        return "personal/editInfo";
    }
	
	@GetMapping(value="/personal_category_list")
    public String listCategory(HttpSession session){
		User user =(User)session.getAttribute("user");
		 if(null==user){
			 return "fore/login";			 
		 }
		 session.setAttribute("user", user);
        return "personal/personal_category_list";
    }
	
	@GetMapping(value="/personal_product_list")
    public String listProduct(HttpSession session){
		User user =(User)session.getAttribute("user");
		 if(null==user){
			 return "fore/login";			 
		 }
		 session.setAttribute("user", user);
        return "personal/personal_product_list";
    }
	//产品图片跳转
	@GetMapping(value="/personal_productImage_list")
	public String listProductImage(){
		return "personal/listPersonalProductImage";
	}
	
	@GetMapping(value="/personal_productExplain_list")
	public String listProductExplain(){
		return "personal/listPersonalProductExplain";
	}
	
	@GetMapping(value="/personal_propertyValue_edit")
	public String listProductPropertyValue(){
		return "personal/editPersonalPropertyValue";
	}
	
	@GetMapping(value="/personal_product_edit")
	public String editPersonalProduct(){
		return "personal/editPersonalProduct";
	}
	
	@GetMapping(value="/personal_demand_list")
	public String listPersonalDemand(HttpSession session){
		User user =(User)session.getAttribute("user");
		 if(null==user){
			 return "fore/login";			 
		 }
		 session.setAttribute("user", user);
		return "personal/listPersonalDemand";
	}
	
	@GetMapping("/personal_demandmatch_list")
	public String listDemandMatch(HttpSession session){
		User user =(User)session.getAttribute("user");
		 if(null==user){
			 return "fore/login";			 
		 }
		 session.setAttribute("user", user);
		return "personal/listPatentMatch";
	}
}
