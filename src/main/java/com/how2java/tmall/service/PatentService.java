package com.how2java.tmall.service;

import com.how2java.tmall.dao.PatentDAO;
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
public class PatentService {

    @Autowired
    PatentDAO patentDAO;

    public Patent getById(int id) {
        return patentDAO.findById(id);
    }

    public Page4Navigator<Patent> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = patentDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }
    public List<Patent> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return patentDAO.findAll(sort);
    }

    public void delete(int id) {
        patentDAO.delete(id);
    }

    public Patent get(int id) {
        Patent p = patentDAO.findOne(id);
        return p;
    }

    public void update(Patent bean) {
        patentDAO.save(bean);
    }

    /**
     * 根据关键词查询专利
     */
    public List<Patent> search(String keyword){
     
        List<Patent> patents = patentDAO.findByPatentTitleLike("%" + keyword + "%");
        return patents;
    }

    public List<Patent> topicSearch(String keyword){

        List<Patent> patents = patentDAO.findByPatentTitleLike("%" + keyword + "%");
        return patents;
    }
}

