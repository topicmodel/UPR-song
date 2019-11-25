package com.how2java.tmall.service;

import com.how2java.tmall.dao.InventorDAO;
import com.how2java.tmall.pojo.Inventor;
import com.how2java.tmall.pojo.Patent;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventorService {
    @Autowired
    InventorDAO inventorDAO;

    public Inventor getById(int id) {
        return inventorDAO.findById(id);
    }

    public Inventor findUserByUniversityAndName(String university, String name){
        return inventorDAO.findByUniversityLikeAndInventorNameLike(university,name);
    }

    public Page4Navigator<Inventor> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = inventorDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }
    public List<Inventor> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return inventorDAO.findAll(sort);
    }
    public void add(Inventor bean) {
        inventorDAO.save(bean);
    }

    public void delete(int id) {
        inventorDAO.delete(id);
    }

    public Inventor get(int id) {
        Inventor inventor = inventorDAO.findOne(id);
        return inventor;
    }

    public void update(Inventor bean) {
        inventorDAO.save(bean);
    }
}
