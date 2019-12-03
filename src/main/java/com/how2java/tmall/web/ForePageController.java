package com.how2java.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ForePageController {
	@GetMapping(value="/")
	public String index(){
		return "redirect:center";
	}
	
	@GetMapping(value="/center")
	public String center(){
		return "fore/center";
	}
	
/*	@GetMapping(value="/home")
	public String home(){
		return "fore/home";
	}*/
	
	@GetMapping(value="/register")
	public String register(){
		return "fore/register";
	}
	@GetMapping(value="/userInfo")
	public String userInfo(){
		return "fore/userInfo";
	}
	
	
	@GetMapping(value="/alipay")
	public String alipay(){
		return "fore/alipay";
	}
	@GetMapping(value="/bought")
	public String bought(){
		return "fore/bought";
	}

	@GetMapping(value="/buy")
	public String buy(){
		return "fore/buy";
	}
	@GetMapping(value="/cart")
	public String cart(){
		return "fore/cart";
	}
	@GetMapping(value="/category")
	public String category(){
		return "fore/category";
	}
	@GetMapping(value="/confirmPay")
	public String confirmPay(){
		return "fore/confirmPay";
	}
	@GetMapping(value="/login")
	public String login(){
		return "fore/login";
	}
	@GetMapping(value="/orderConfirmed")
	public String orderConfirmed(){
		return "fore/orderConfirmed";
	}
	@GetMapping(value="/payed")
	public String payed(){
		return "fore/payed";
	}
	@GetMapping(value="/product")
	public String product(){
		return "fore/product";
	}
	//跳转到知产服务详情页
	@GetMapping(value="/rightservice")
	public String rightService(){
		return "fore/rightService";
	}
	@GetMapping(value="/registerSuccess")
	public String registerSuccess(){
		return "fore/registerSuccess";
	}
	@GetMapping(value="/review")
	public String review(){
		return "fore/review";
	}

	//跳转到发明人界面
	@GetMapping(value = "inventorSearch")
	public String searchInventor(){
		return "fore/inventorSearch";
	}
	//
	@GetMapping(value = "inventorDetail")
	public String inventorDetail(){
		return "fore/inventorDetail";
	}
	//请求页面
	@GetMapping(value="/search")
	public String searchResult(){
		return "fore/search";
	}

	//热词请求页面
	@GetMapping(value="/topicSearch")
	public String topicSearchResult(){
		return "fore/topicSearch";
	}

	@GetMapping("/forelogout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:center";
	}
	
	@GetMapping("/demandlist")
	public String demandlist(){
		return "fore/demand";
	}
	@GetMapping("/agencylist")
	public String agencylist(){
		return "fore/agency";
	}
	
	@GetMapping("/shudacenter")
	public String centerPage(){
		return "fore/shudacenter";
	}
	
	@GetMapping("/shudashop")
	public String shudaShop(){
		return "fore/shudashop";
	}
	
	@GetMapping("/demandinfo")
	public String demandDesc(){
		return "fore/demandInfo";
	}
	
	@GetMapping(value = "/patentbrokerlist")
	public String patentBrokerList(){
		return "fore/brokerlist";
	}
}
