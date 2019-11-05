package com.how2java.tmall.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.how2java.tmall.pojo.Demand;
import com.how2java.tmall.pojo.User;

public interface DemandDAO extends JpaRepository<Demand, Integer>{
	Page<Demand> findByUser(User user, Pageable pageable);
	
	Page<Demand> findAll(Pageable pageable);
}
