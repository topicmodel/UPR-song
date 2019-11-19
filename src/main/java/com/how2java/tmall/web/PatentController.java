package com.how2java.tmall.web;
import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.InventorService;
import com.how2java.tmall.service.PatentService;
import com.how2java.tmall.util.Page4Navigator;
import com.how2java.tmall.util.Result;
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
    @Autowired
    PatentService patentService;

    @Autowired
    InventorService inventorService;

    @GetMapping("/patents")
    public Page4Navigator<Patent> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<Patent> page = patentService.list(start, size, 5);
        return page;
    }

    @DeleteMapping("/patents/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        patentService.delete(id);
        return null;
    }

    @GetMapping("/patents/{id}")
    public Patent get(@PathVariable("id") int id) throws Exception {
        Patent bean = patentService.get(id);
        return bean;
    }

    @PutMapping("/patents/{id}")
    public Object update(@RequestBody Patent bean) throws Exception {
        //  System.out.println("bean:"+bean.toString());
        System.out.println("bean:" + bean.getPatentInventor());
        patentService.update(bean);
        return bean;
    }

    /**
     * 根据keyword检索专利
     *
     * @param keyword
     * @return
     */
    @PostMapping("/foresearch")
    public Object search(String keyword) {

        List<Patent> ps = patentService.search("%"+keyword+"%");
        String patentTitles = null;
        for (Patent p : ps) {
            patentTitles += p.getPatentTitle();
        }
        System.out.println("title:" + patentTitles);

        TFIDFAnalyzer tfidfAnalyzer = new TFIDFAnalyzer();
        List<Keyword> list = tfidfAnalyzer.analyze(patentTitles, 10);
        return list;
    }

    /**
     * 根据topicWords检索专利,并返回专利权人
     *
     * @param keyword
     * @return
     */
    @PostMapping("/analyzeSchool")
    public Object topicSearch(String keyword) {
        if (null == keyword) {
            keyword = "";
        }
        List<Patent> ps = patentService.topicSearch("%"+keyword+"%");
        List<String> persons = new ArrayList<String>();
        //取出含有大学的applyPerson
        for (Patent p : ps) {

            if (p.getApplyPerson().contains("大学")) {
                if (p.getApplyPerson().contains(";")) {
                    continue;
                }
                persons.add(p.getApplyPerson());
            }
        }

        /*Map<String,Integer> hashMap =new HashMap<>();
        for(String str:persons){
            if (str!=null || "".equals(str)) {
                if(hashMap.containsKey(str)){
                    hashMap.put(str,hashMap.get(str)+1);
                }else{
                    hashMap.put(str,1);
                }
            }
        }
        List<ApplyPerson> newPerson = new ArrayList<>();
        for(Map.Entry<String,Integer> m:hashMap.entrySet()){
            ApplyPerson applyPerson = new ApplyPerson();
            applyPerson.setName(m.getKey());
            applyPerson.setNumber(m.getValue());
            newPerson.add(applyPerson);
        }*/
        //Begin：高校名称+高校数量
        List<ApplyPerson> applyPersons = new ArrayList<>();
        for (String str : persons) {
            ApplyPerson applyPerson = new ApplyPerson();
            applyPerson.setName(str);
            applyPerson.setNumber(1);
            applyPersons.add(applyPerson);
        }

        Map<String, ApplyPerson> hashMap = new HashMap<>();
        for (ApplyPerson person : applyPersons) {
            if (person.getName() != null || "".equals(person.getName())) {
                if (hashMap.containsKey(person.getName())) {
                    int num = person.getNumber();
                    num += hashMap.get(person.getName()).getNumber();
                    hashMap.get(person.getName()).setNumber(num);
                } else {
                    hashMap.put(person.getName(), person);
                }
            }
        }

        List<ApplyPerson> newPerson = new ArrayList<>();


        for (ApplyPerson person : hashMap.values()) {
            newPerson.add(person);
        }
        //End：高校名称+高校数量

        Collections.sort(newPerson);
/*        for (ApplyPerson p : newPerson) {
            System.err.println("num:" + p.getNumber() + "," + p.getName());
        }*/

/*        Map<String, Object> map = new HashMap<>();
        map.put("university", newPerson);*/
        //System.err.println("applyPerson" + applyPersons);
        return newPerson;

    }
    /**
     * 根据university和keyword检索专利，并返回发明人
     *
     */
    @PostMapping("/analyseInventor")
    public Object inventorSearch(String university, String keyword) {
        System.out.println(university+","+keyword);
        //检索
        List<Patent> patents = patentService.inventorSearch(university, "%"+keyword+"%");

        //发明人链条集合
        List<InventorLink>  links = new ArrayList<>();
        List<String> inventors =new ArrayList<>();
        //关系链条整理
        for(Patent p: patents){
            String patentInventor = p.getPatentInventor();
            inventors.add(patentInventor);
        }

        for(String str:inventors){

            if(str.indexOf(";")==0){
                continue;
            }
            String[] split = str.split(";");

            for(int i=1;i<split.length;i++){
                System.out.println(split[i-1]);
                InventorLink link = new InventorLink();
                link.setSource(split[i-1]);
                link.setTarget(split[i]);
                link.setName("合作");
                links.add(link);
            }

        }


        System.err.println(patents.size());
        //创建发明人集合
        List<String> patentinventors = new ArrayList<>();

        //装发明人
        for (Patent p:patents){
            String inventorNames = p.getPatentInventor();
            if(inventorNames.indexOf(";")!=0){
                String[] strInventor = inventorNames.split(";");
                for(String str:strInventor){
                    patentinventors.add(str);
                }
            }else{
                patentinventors.add(inventorNames);
            }
        }


        //统计发明人数
        Map<String,Integer> map = new HashMap<>();
        for(String str:patentinventors){
            if(str !=null || "".equals(str)){
                if(map.containsKey(str)){
                    map.put(str,map.get(str)+1);
                }else {
                    map.put(str,1);
                }
            }
        }
        List<PatentInventor> patentInventors = new ArrayList<>();
        for(Map.Entry<String,Integer> m:map.entrySet()){
            PatentInventor inventor =new PatentInventor();
            inventor.setInventorName(m.getKey());
            inventor.setInventorNum(m.getValue());
            patentInventors.add(inventor);
        }
        Collections.sort(patentInventors);

        Map<String,Object> hashMap = new HashMap<>();
        hashMap.put("link",links);
        hashMap.put("inventor",patentInventors);

        return hashMap;
    }
    //发明人详情资料
    @PostMapping("/detailInventor")
    public Object inventorDetail(String university,String inventor){
        Inventor inventer = inventorService.findUserByUniversityAndName("%"+university+"%", "%"+inventor+"%");
        List<Patent> patents = patentService.inventorDetail("%" + university + "%", "%" + inventor + "%");
        Map<String,Object> map= new HashMap<>();
        map.put("inventor",inventer);
        map.put("patent",patents);
        return map;
    }

}