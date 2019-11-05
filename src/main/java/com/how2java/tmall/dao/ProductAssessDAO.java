package com.how2java.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductAssess;

public interface ProductAssessDAO extends JpaRepository<ProductAssess, Integer>{
	
	public ProductAssess findByProduct(Product product);
	
}
