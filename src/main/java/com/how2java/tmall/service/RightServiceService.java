package com.how2java.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.RightServiceDAO;
import com.how2java.tmall.pojo.PatentDomain;
import com.how2java.tmall.pojo.RightService;
import com.how2java.tmall.util.Page4Navigator;

@Service
public class RightServiceService {
	@Autowired
	RightServiceDAO rightServiceDAO;
	
	@Autowired
	PatentDomainService patentDomainService;
	
	@Autowired
	RightServiceImageService rightServiceImageService;
	
	public void delete(int pdid){
		rightServiceDAO.delete(pdid);
	}
	
	public void update(RightService bean){
		rightServiceDAO.save(bean);
	}
	
	public RightService get(int id){
		return rightServiceDAO.findOne(id);
	}
	
	public void add(RightService bean){
		rightServiceDAO.save(bean);
	}
	
	//分页查询
	public Page4Navigator<RightService> list(int pdid,int start,int size,int navigatePages){
		PatentDomain domain = patentDomainService.get(pdid);
		Sort sort =new Sort(Sort.Direction.DESC,"id");
		Pageable pageable=new PageRequest(start, size,sort);
		Page<RightService> pageFromJPA = rightServiceDAO.findByPatentDomain(domain, pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	//查询某个领域下的知产服务
	public List<RightService> listByPatentDomain(PatentDomain bean){
		return rightServiceDAO.findByPatentDomain(bean);
	}
	//为每个领域填充知产服务
	public void fill(List<PatentDomain> domains){
		for(PatentDomain domain : domains){
			fill(domain);
		}
	}
	public void fill(PatentDomain  domain){
		List<RightService> services = listByPatentDomain(domain);
		rightServiceImageService.setFirstRigthServiceImages(services);
		domain.setServices(services);
	}
}
