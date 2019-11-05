package com.how2java.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.AgencyDAO;
import com.how2java.tmall.pojo.Agency;
import com.how2java.tmall.util.Page4Navigator;

@Service
public class AgencyService {

	@Autowired
	AgencyDAO agencyDAO;

	public Page4Navigator<Agency> list(int start, int size, int navigatePages) {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<Agency> pageFromJPA = agencyDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}

	public List<Agency> list() {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		return agencyDAO.findAll(sort);
	}

	// 增加公司
	public void add(Agency agency) {
		agencyDAO.save(agency);
	}

	// 删除公司
	public void delete(int id) {
		agencyDAO.delete(id);
	}
	//根据id查询某一公司
	public Agency get(int id){
		return agencyDAO.findOne(id);
	}
}
