package com.how2java.tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.RightServiceOrderItem;

public interface RightServiceOrderItemDAO extends JpaRepository<RightServiceOrderItem, Integer>{
	//根据订单查询知产服务订单项
	List<RightServiceOrderItem> findByOrderOrderByIdDesc(Order order);
	
}
