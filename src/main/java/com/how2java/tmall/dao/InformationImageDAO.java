package com.how2java.tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.how2java.tmall.pojo.Information;
import com.how2java.tmall.pojo.InformationImage;

public interface InformationImageDAO extends JpaRepository<InformationImage, Integer>{
	public List<InformationImage> findByInformationAndTypeOrderByIdDesc(Information information,String type);
}
