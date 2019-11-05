package com.how2java.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.how2java.tmall.pojo.PatentDomain;

public interface PatentDomainDAO extends JpaRepository<PatentDomain, Integer>{
	
}
