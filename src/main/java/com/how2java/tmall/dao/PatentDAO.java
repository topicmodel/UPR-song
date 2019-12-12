package com.how2java.tmall.dao;


import com.how2java.tmall.pojo.Patent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatentDAO extends JpaRepository<Patent,Integer>{
    
    Patent findById(int id);
    //根据关键词查询专利
    /*List<Patent> findByPatentTitleLike(String keyword);*/
    List<Patent> findByPatentDescLike(String keyword);
    //根据university和Patent检索
    /*List<Patent> findByApplyPersonLikeAndPatentTitleLike(String university,String keyword);*/
    List<Patent> findByApplyPersonLikeAndPatentDescLike(String university,String keyword);
    //根据university和检索patentInventor查找

    List<Patent> findByApplyPersonLikeAndPatentInventorLike(String university,String inventor);
}
