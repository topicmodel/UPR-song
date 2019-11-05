package com.how2java.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.ProductExplainDAO;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductExplain;

@Service
public class ProductExplainService {
	public static final String type_single = "abstract";
	public static final String type_detail = "right";

	@Autowired
	ProductExplainDAO productExplainDAO;
	@Autowired
	ProductService productService;

	public void add(ProductExplain bean) {
		productExplainDAO.save(bean);
	}

	// 获取文件列表
	public List<ProductExplain> listProductAbstract(Product bean) {
		return productExplainDAO.findByProductAndTypeOrderByIdDesc(bean, type_single);
	}

	public List<ProductExplain> listProductRight(Product bean) {
		return productExplainDAO.findByProductAndTypeOrderByIdDesc(bean, type_detail);
	}

	// 删除
	public void delete(int id) {
		productExplainDAO.delete(id);
	}

	// 获取单个说明
	public ProductExplain get(int id) {
		return productExplainDAO.findOne(id);
	}
}
