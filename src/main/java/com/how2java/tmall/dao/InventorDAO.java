package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Inventor;
import com.how2java.tmall.pojo.Patent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventorDAO  extends JpaRepository<Inventor,Integer> {

    Inventor findByUniversityLikeAndInventorNameLike(String university,String name);
}
