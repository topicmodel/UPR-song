package com.how2java.tmall.web;
import com.how2java.tmall.pojo.Patent;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.PatentService;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class PatentController {
    @Autowired PatentService patentService;

    @GetMapping("/patents")
    public Page4Navigator<Patent> list(@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Patent> page = patentService.list(start,size,5);
        return page;
    }

    @DeleteMapping("/patents/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        patentService.delete(id);
        return null;
    }

    @GetMapping("/patents/{id}")
    public Patent get(@PathVariable("id") int id) throws Exception {
        Patent bean=patentService.get(id);
        return bean;
    }

    @PutMapping("/patents/{id}")
    public Object update(@RequestBody Patent bean) throws Exception {
      //  System.out.println("bean:"+bean.toString());
        System.out.println("bean:"+bean.getPatentInventor());
        patentService.update(bean);
        return bean;
    }

    /**
     * 根据keyword检索专利
     * @param keyword
     * @return
     */
    @PostMapping("/foresearch")
    public Object search(String keyword){
        if(null == keyword){
            keyword = "";
        }
        List<Patent> ps = patentService.search(keyword);
        System.err.println("ps.length:"+ps.size());
        return ps;
    }
}
