package com.how2java.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.RightServiceImageDAO;
import com.how2java.tmall.pojo.RightService;
import com.how2java.tmall.pojo.RightServiceImage;

@Service
public class RightServiceImageService {

	public static final String type_single = "single";
	public static final String type_detail = "detail";

	@Autowired
	RightServiceImageDAO rightServiceImageDAO;
	@Autowired
	RightServiceService rightServiceService;

	// 增加知产服务图片
	public void add(RightServiceImage bean) {
		rightServiceImageDAO.save(bean);
	}

	// 删除知产服务图片
	public void delete(int id) {
		rightServiceImageDAO.delete(id);
	}

	// 获取单张图片
	public RightServiceImage get(int id) {
		return rightServiceImageDAO.findOne(id);
	}

	// 获取主图列表
	public List<RightServiceImage> listSingleRightServiceImages(RightService bean) {
		System.err.println("执行查询语句！");
		List<RightServiceImage> beans = rightServiceImageDAO.findByRightServiceAndTypeOrderByIdDesc(bean, type_single);
		System.err.println("beans:"+beans);
		return beans;
	}

	// 为知产服务设置第一张图
	public void setFirstRigthServiceImages(List<RightService> rightServices) {
		for (RightService rightService : rightServices) {
			setFirstRightServiceImage(rightService);
		}
	}
	
	public void setFirstRightServiceImage(RightService rightService) {
		List<RightServiceImage> singleImages = listSingleRightServiceImages(rightService);
		if (!singleImages.isEmpty()) {
			rightService.setFirstRightServiceImage(singleImages.get(0));
		} else {
			rightService.setFirstRightServiceImage(new RightServiceImage());
		}
	}
}
