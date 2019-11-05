package com.how2java.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.DemandDAO;
import com.how2java.tmall.pojo.Demand;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.util.Page4Navigator;

@Service
public class DemandService {
	@Autowired
	DemandDAO demandDAO;
	@Autowired
	UserService userService;
	//获取特定用户需求
	public Page4Navigator<Demand> list(int uid, int start, int size,int navigatePages) {
    	User user= userService.getById(uid);
    	Sort sort = new Sort(Sort.Direction.DESC, "id");
    	Pageable pageable = new PageRequest(start, size, sort);    	
    	Page<Demand> pageFromJPA =demandDAO.findByUser(user,pageable);
    	return new Page4Navigator<>(pageFromJPA,navigatePages);
	}
	//获取需求列表
	public Page4Navigator<Demand> findAll(int start,int size,int navigatePages){
		Sort sort=new Sort(Sort.Direction.DESC,"id");
		Pageable pageable=new PageRequest(start, size, sort);
		Page<Demand> pageFromJPA = demandDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA,navigatePages);
	}
	
	public void add(Demand bean) {
		demandDAO.save(bean);
	}

	public void delete(int id) {
		demandDAO.delete(id);
	}

	public Demand get(int id) {
		return demandDAO.findOne(id);
	}

	public void update(Demand bean) {
		demandDAO.save(bean);
	}
	
	public List<Demand> findAll(){
		return demandDAO.findAll();
	}
}
