package com.how2java.tmall.web;
import com.how2java.tmall.pojo.Inventor;
import com.how2java.tmall.pojo.Patent;
import com.how2java.tmall.service.InventorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.UserService;
import com.how2java.tmall.util.Page4Navigator;

import javax.servlet.http.HttpServletRequest;

@RestController
public class InventorController {
    @Autowired InventorService inventorService;

    @GetMapping("/inventors")
    public Page4Navigator<Inventor> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Inventor> page = inventorService.list(start,size,5);
        return page;
    }
    @DeleteMapping("/inventors/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        inventorService.delete(id);
        return null;
    }

    @GetMapping("/inventors/{id}")
    public Inventor get(@PathVariable("id") int id) throws Exception {
        Inventor bean = inventorService.get(id);
        return bean;
    }

    @PutMapping("/inventors/{id}")
    public Object update(@RequestBody Inventor bean) throws Exception {
        //  System.out.println("bean:"+bean.toString());
        inventorService.update(bean);
        return bean;
    }

}


