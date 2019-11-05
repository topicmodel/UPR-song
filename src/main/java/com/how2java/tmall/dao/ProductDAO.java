package com.how2java.tmall.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.User;

public interface ProductDAO extends JpaRepository<Product, Integer> {
	Page<Product> findByCategory(Category category, Pageable pageable);

	List<Product> findByCategoryOrderById(Category category);
	
	//根据分类查询，按score排序
	List<Product> findByCategoryOrderByScoreDesc(Category category);

	List<Product> findByNameLike(String keyword, Pageable pageable);

	Page<Product> findByCategoryAndUser(Category category, User user, Pageable pageable);
	
	//根据匹配值查询
	Page<Product> findByOrderByMatchScoreDesc(Pageable pageable);
	
}
