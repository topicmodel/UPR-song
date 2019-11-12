package com.how2java.tmall.web;
import com.how2java.tmall.pojo.Patent;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.PatentService;
import com.how2java.tmall.util.Page4Navigator;
import com.how2java.tmall.util.jieba.Keyword;
import com.how2java.tmall.util.jieba.TFIDFAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static com.hankcs.hanlp.classification.utilities.CollectionUtility.sortMapByValue;
import static org.thymeleaf.util.DartUtils.printMap;

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
        String patentTitles = null;
        /*for(int i = 0;i<ps.size();i++){
            String title = ps.get(i).getPatentTitle();
            patentTitles[i]=title;
        }*/
        for(Patent p: ps){
            patentTitles+=p.getPatentTitle();
        }
        System.out.println("title:"+patentTitles);

        //int topN=5;
        TFIDFAnalyzer tfidfAnalyzer=new TFIDFAnalyzer();
        List<Keyword> list=tfidfAnalyzer.analyze(patentTitles,10);
       /* for(Keyword word:list)
            System.out.print(word.getName()+":"+word.getTfidfvalue()+",");*/


        //System.err.println("ps.length:"+ps.size());
        return list;
    }

    /**
     * 根据topicWords检索专利
     * @param keyword
     * @return
     */
    @PostMapping("/analyzeSchool")
    public Object topicSearch(String keyword) {
        if (null == keyword) {
            keyword = "";
        }
        List<Patent> ps = patentService.topicSearch(keyword);
        List<String> applyPersons = new ArrayList<String>();
        for (Patent p : ps) {

            if (p.getApplyPerson().contains("大学")) {
                if (p.getApplyPerson().contains(";")) {
                    continue;
                }
                applyPersons.add(p.getApplyPerson());
            }
        }
        System.err.println("applyPerson" + applyPersons);


        return applyPersons;
    }

}
