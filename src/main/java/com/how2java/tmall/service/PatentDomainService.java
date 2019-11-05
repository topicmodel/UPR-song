package com.how2java.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.PatentDomainDAO;
import com.how2java.tmall.pojo.PatentDomain;
import com.how2java.tmall.pojo.RightService;
import com.how2java.tmall.util.Page4Navigator;

@Service
public class PatentDomainService {

	@Autowired
	PatentDomainDAO patentDomainDAO;

	// 获取专业领域列表数据
	public List<PatentDomain> list() {
		Sort sort = new Sort(Sort.Direction.ASC, "id");
		return patentDomainDAO.findAll(sort);
	}

	// 根据id查询某一专业领域
	public PatentDomain get(Integer domainid) {
		return patentDomainDAO.findOne(domainid);
	}

	// 知产服务分页查询
	public Page4Navigator<PatentDomain> list(int start, int size, int navigatePages) {
		Sort sort = new Sort(Sort.Direction.ASC, "id");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<PatentDomain> pageFromJPA = patentDomainDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}

	// 增加知产服务分类
	public void add(PatentDomain bean) {
		patentDomainDAO.save(bean);
	}

	// 知产服务分类删除
	public void delete(PatentDomain bean) {
		patentDomainDAO.delete(bean);
	}

	public void delete(int id) {
		patentDomainDAO.delete(id);
	}

	// 更新服务分类名称
	public void update(PatentDomain bean) {
		patentDomainDAO.save(bean);
	}

	// 删除rightService上的patentDomain,防止转化为JSON时递归调用
	public void removePatentDomainFromRigtService(List<PatentDomain> domains) {
		for (PatentDomain domain : domains) {
			removePatentDomainFromRigtService(domain);
		}
	}

	public void removePatentDomainFromRigtService(PatentDomain domain) {
		List<RightService> services = domain.getServices();
		if (services != null) {
			for (RightService service : services) {
				service.setPatentDomain(null);
			}
		}
	}
}
