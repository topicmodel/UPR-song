package com.how2java.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.InformationImageDAO;
import com.how2java.tmall.pojo.Information;
import com.how2java.tmall.pojo.InformationImage;

@Service
public class InformationImageService {
	
	public static final String type_single = "single";
    public static final String type_detail = "detail";
    
    @Autowired
    InformationImageDAO informationImageDAO;
    @Autowired
    InformationService informationService;
    
    public void add(InformationImage bean){
    	informationImageDAO.save(bean);
    }
    
    public InformationImage get(int id){
    	return informationImageDAO.findOne(id);
    }
    
    public void delete(int id){
    	informationImageDAO.delete(id);
    }
    
    //查询单张图片
    public List<InformationImage> listSingleInformationImage(Information information){
    	return informationImageDAO.findByInformationAndTypeOrderByIdDesc(information,type_single);
    }
   //查询详情图片
    public List<InformationImage> listDetailInformationImage(Information information){
    	return informationImageDAO.findByInformationAndTypeOrderByIdDesc(information,type_detail);
    }
    
    //设置一条信息的第一张图片
    public void setFirstInformationImage(Information information){
    	List<InformationImage> singleImages =listSingleInformationImage(information);
    	if(!singleImages.isEmpty()){
    		information.setFirstInformationImage(singleImages.get(0));
    	}else{
    		information.setFirstInformationImage(new InformationImage());
    	}
    }
    //设置所有信息的第一张图片
    public void setFirstInformationImages(List<Information> informations) {
        for (Information information : informations)
            setFirstInformationImage(information);
    }
}
