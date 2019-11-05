package com.how2java.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.InfoCategoryDAO;
import com.how2java.tmall.pojo.InfoCategory;
import com.how2java.tmall.pojo.Information;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.util.Page4Navigator;

@Service
public class InfoCategoryService {
	@Autowired
	InfoCategoryDAO infoCategoryDAO;
	
	public Page4Navigator<InfoCategory> list(int start,int size,int navigatePages){
		Sort sort =new  Sort(Sort.Direction.ASC,"id");
		Pageable pageable =new PageRequest(start, size, sort);
		Page pageFromJPA = infoCategoryDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public List<InfoCategory> list(){
		Sort sort =new Sort(Sort.Direction.ASC,"id");
		return infoCategoryDAO.findAll(sort);
	}
	
	public InfoCategory get(int id){
		return infoCategoryDAO.findOne(id);
	}
	
	public void removeInfoCategoryFromInformation(List<InfoCategory> ics) {
		for (InfoCategory infoCategory : ics) {
			removeInfoCategoryFromInformation(infoCategory);
		}
	}

	public void removeInfoCategoryFromInformation(InfoCategory ics) {
		List<Information> informations = ics.getInformations();
		if (null != informations) {
			for (Information info : informations) {
				info.setInfoCategory(null);;
			}
		}
	}
}	
