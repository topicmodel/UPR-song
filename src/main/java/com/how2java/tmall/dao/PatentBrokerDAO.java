package com.how2java.tmall.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.how2java.tmall.pojo.Agency;
import com.how2java.tmall.pojo.PatentBroker;
import com.how2java.tmall.pojo.PatentDomain;

public interface PatentBrokerDAO extends JpaRepository<PatentBroker, Integer>{
	
	//根据代理机构查询专利经纪人
	Page<PatentBroker> findByAgency(Agency agency,Pageable pageable);
	//根据专业领域查找专利经纪人
	Page<PatentBroker> findByPatentDomain(PatentDomain bean,Pageable pageable);
}
