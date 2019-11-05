package com.how2java.tmall.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.InformationDAO;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.InfoCategory;
import com.how2java.tmall.pojo.Information;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.util.Page4Navigator;

@Service
public class InformationService {
	@Autowired
	InformationDAO informationDAO;
	@Autowired
	InfoCategoryService infoCategoryService;
	@Autowired
	InformationImageService informationImageService;
	
	/**
	 * 增加信息
	 * 
	 * @param info
	 */
	public void add(Information info) {
		informationDAO.save(info);
	}

	public void delete(int id) {
		informationDAO.delete(id);
	}

	public Information get(int id) {
		return informationDAO.findOne(id);
	}

	public void update(Information bean) {
		informationDAO.save(bean);
	}

	public Page4Navigator<Information> list(int infoid, int start, int size, int navigatePages) {
		InfoCategory infoCategory = infoCategoryService.get(infoid);
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<Information> pageFromJPA = informationDAO.findByInfoCategory(infoCategory, pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	// 根据id排序，获取产品列表
		public List<Information> listByInfoCategory(InfoCategory infoCategory) {
			return informationDAO.findByInfoCategoryOrderById(infoCategory);
		}
	
	//为多个分类填充商品集合
	public void fill(List<InfoCategory> infoCategorys){
		for(InfoCategory infoCategory:infoCategorys){
			fill(infoCategory);
		}
	}
	//为一个分类填充商品集合
	public void fill(InfoCategory infoCategory){
		List<Information> infos =  listByInfoCategory(infoCategory);
		informationImageService.setFirstInformationImages(infos);
		infoCategory.setInformations(infos);
	}
}
