package com.how2java.tmall.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.how2java.tmall.pojo.InfoCategory;
import com.how2java.tmall.pojo.Information;

public interface InformationDAO extends JpaRepository<Information, Integer>{
	Page<Information> findByInfoCategory(InfoCategory infoCategory,Pageable pageable);
	//根据内容分类查询信息
	List<Information> findByInfoCategoryOrderById(InfoCategory infoCategory);
}
