package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Inventor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventorDAO  extends JpaRepository<Inventor,Integer> {
    Inventor findById(int id);
    Inventor findByUniversityLikeAndInventorNameLike(String university,String name);
   
}
