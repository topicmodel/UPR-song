package com.how2java.tmall.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.how2java.tmall.pojo.PatentDomain;
import com.how2java.tmall.pojo.RightService;

public interface RightServiceDAO extends JpaRepository<RightService, Integer>{
	
	//根据领域查询知产服务
	Page<RightService> findByPatentDomain(PatentDomain domain,Pageable pageable);
	//根据领域查询知产服务
	List<RightService> findByPatentDomain(PatentDomain domain);
}
