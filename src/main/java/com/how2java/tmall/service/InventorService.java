package com.how2java.tmall.service;

import com.how2java.tmall.dao.InventorDAO;
import com.how2java.tmall.pojo.Inventor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventorService {
    @Autowired
    InventorDAO inventorDAO;

    public Inventor findUserByUniversityAndName(String university, String name){
        return inventorDAO.findByUniversityLikeAndInventorNameLike(university,name);
    }
}
