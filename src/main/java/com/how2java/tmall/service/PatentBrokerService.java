package com.how2java.tmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.PatentBrokerDAO;
import com.how2java.tmall.pojo.Agency;
import com.how2java.tmall.pojo.PatentBroker;
import com.how2java.tmall.pojo.PatentDomain;
import com.how2java.tmall.util.Page4Navigator;

@Service
public class PatentBrokerService {

	@Autowired
	PatentBrokerDAO patentBrokerDAO;

	@Autowired
	AgencyService agencyService;

	@Autowired
	PatentDomainService patentDomainService;

	// 增加经纪人
	public void add(PatentBroker broker) {
		patentBrokerDAO.save(broker);
	}

	// 删除经纪人
	public void delete(int id) {
		patentBrokerDAO.delete(id);
	}

	// 更新经济人
	public void update(PatentBroker bean) {
		patentBrokerDAO.save(bean);
	}

	// 根据id查询经纪人
	public PatentBroker get(int id) {
		return patentBrokerDAO.findOne(id);
	}

	// 根据代理机构查询分页数据
	public Page4Navigator<PatentBroker> list(int agencyid, int start, int size, int navigatePages) {
		Agency agency = agencyService.get(agencyid);
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<PatentBroker> pageFromJPA = patentBrokerDAO.findByAgency(agency, pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}

	// 根据专业领域查询分页数据
	public Page4Navigator<PatentBroker> listByDomain(int domainid, int start, int size, int navigatePages) {
		PatentDomain domain = patentDomainService.get(domainid);
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<PatentBroker> pageFromJPA = patentBrokerDAO.findByPatentDomain(domain, pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
}
