package com.how2java.tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.how2java.tmall.pojo.RightService;
import com.how2java.tmall.pojo.RightServiceImage;

public interface RightServiceImageDAO extends JpaRepository<RightServiceImage, Integer>{
	
	public List<RightServiceImage> findByRightServiceAndTypeOrderByIdDesc(RightService rigthService, String type);
}
