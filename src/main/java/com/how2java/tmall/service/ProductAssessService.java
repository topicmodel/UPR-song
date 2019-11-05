package com.how2java.tmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.ProductAssessDAO;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductAssess;

@Service
public class ProductAssessService {

	@Autowired
	ProductAssessDAO productAssessDAO;
	@Autowired
	ProductService productService;

	public ProductAssess get(int pid) {
		Product product = productService.get(pid);
		return productAssessDAO.findByProduct(product);
	}

	// 更新
	public void update(ProductAssess bean) {
		productAssessDAO.save(bean);
	}

	public void add(ProductAssess bean) {
		productAssessDAO.save(bean);
	}
}
