package com.how2java.tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductExplain;

public interface ProductExplainDAO extends JpaRepository<ProductExplain, Integer>{
	public List<ProductExplain> findByProductAndTypeOrderByIdDesc(Product product, String type);
}
