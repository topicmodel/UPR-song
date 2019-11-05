package com.how2java.tmall.web;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductAssess;
import com.how2java.tmall.service.ProductAssessService;
import com.how2java.tmall.service.ProductService;

@RestController
public class ProductAssessController {

	@Autowired
	ProductService productService;
	@Autowired
	ProductAssessService productAssessService;

	@GetMapping("/productassess/{id}")
	public ProductAssess get(@PathVariable("id") int pid) {
		ProductAssess assess = productAssessService.get(pid);
		if(assess!=null){
			return assess;
		}
		ProductAssess bean =new ProductAssess();
		bean.setProduct(productService.get(pid));
		bean.setAbility(0);
		bean.setApplyscale(0);
		bean.setCompete(0);
		bean.setMarketapply(0);
		bean.setMarketshare(0);
		bean.setNeed(0);
		bean.setPolicyapply(0);
		return bean;
	}

	@PutMapping("/productassess/{id}")
	public Object update(
			@PathVariable("id") int pid, 
			@RequestBody ProductAssess bean) {
		
		float score = (float) (
				bean.getMarketapply() * 0.1 + 
				bean.getAbility() * 0.2 + 
				bean.getApplyscale() * 0.15+ 
				bean.getCompete() * 0.15 + 
				bean.getMarketshare() * 0.15 + 
				bean.getNeed() * 0.1
				+ bean.getPolicyapply() * 0.15
				);
		DecimalFormat df = new DecimalFormat("###.00");
		score = Float.parseFloat(df.format(score));

		bean.setScore(score);
		Product product = productService.get(pid);
		product.setScore(score);
		productAssessService.update(bean);
		return bean;
	}
}
