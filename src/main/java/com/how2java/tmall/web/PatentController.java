package com.how2java.tmall.web;
import java.security.Key;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.how2java.tmall.pojo.*;
import com.how2java.tmall.pojo.TopicKeyword;
import com.how2java.tmall.util.LngAndLatUtil;
import com.how2java.tmall.util.lda.Corpus;
import com.how2java.tmall.util.lda.LdaGibbsSampler;
import com.how2java.tmall.util.lda.LdaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.how2java.tmall.service.InventorService;
import com.how2java.tmall.service.PatentService;
import com.how2java.tmall.util.Page4Navigator;
import com.how2java.tmall.util.jieba.Keyword;
import com.how2java.tmall.util.jieba.TFIDFAnalyzer;

@RestController
public class PatentController {
    @Autowired
    PatentService patentService;

    @Autowired
    InventorService inventorService;

    /**
     * 通过分页查询所有专利
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    @GetMapping("/patents")
    public Page4Navigator<Patent> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<Patent> page = patentService.list(start, size, 5);
        return page;
    }

    /**
     * g根据专利id删除专利
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @DeleteMapping("/patents/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        patentService.delete(id);
        return null;
    }

    /**
     * 根据专利id获取专利数据
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/patents/{id}")
    public Patent get(@PathVariable("id") int id) throws Exception {
        Patent bean = patentService.get(id);
        return bean;
    }

    /**
     * 根据专利id更新专利信息
     * @param bean
     * @return
     * @throws Exception
     */
    @PutMapping("/patents/{id}")
    public Object update(@RequestBody Patent bean) throws Exception {
        //  System.out.println("bean:"+bean.toString());
        System.out.println("bean:" + bean.getPatentInventor());
        patentService.update(bean);
        return bean;
    }

    /**
     * 根据keyword检索专利，并进行主题建模
     *
     * @param keyword
     * @return
     */
    @PostMapping("/foresearch")
    public Object search(String keyword) {
        //tf*idf分析器
        TFIDFAnalyzer tfidfAnalyzer = new TFIDFAnalyzer();

        //根据关键词检索专利
        List<Patent> ps = patentService.search("%"+keyword+"%");
        //定义专利描述集合
        List<String> descs = new ArrayList<>();
        for (Patent p : ps) {
            descs.add(p.getPatentDesc());
        }

/*        String strs = "";
        for (String str : descs) {
            strs += str;
            //先合并文献获取每个词的值
        }
        System.err.println("合并："+strs);
        //合併分詞計算結果
        List<Keyword> keywords = tfidfAnalyzer.analyze(strs);
        System.out.println("****************1*****************");

        for (Keyword k : keywords) {
            System.out.print(k.getName() + "," + k.getTfidfvalue() + ".");
        }
        System.out.println();
        System.out.println("****************1*****************");*/

        List<String> wordList = new LinkedList<String>();
        //每件专利描述进行分词处理
        for(String sentence : descs){

            List<Keyword> finalWord = new ArrayList<>();
            //获取分词结果以及对应的tf*idf值
            List<Keyword> analyze = tfidfAnalyzer.analyze(sentence);
            for(Keyword word : analyze){
                //字符串长度小于2去除
                if (word.getName().trim().length()<3){
                    continue;
                }
                Keyword kw = new Keyword();

                kw.setName(word.getName());
                kw.setTfidfvalue(word.getTfidfvalue());
                finalWord.add(kw);
            }
            //排序，从高到低
            /*Collections.sort(finalWord);*/
            Collections.sort(finalWord, new Comparator<Keyword>() {
                @Override
                public int compare(Keyword o1, Keyword o2) {  //必须有返回0的情况，否则报错
                    if ( o2.getTfidfvalue()>o1.getTfidfvalue()) return 1;
                    else if (o1.getTfidfvalue()==o2.getTfidfvalue())return 0;
                    else return -1;
                }
            });
            //保留值最高的10个词
            if (finalWord.size() > 10) {
                int num = finalWord.size() - 10;
                for (int i = 0; i < num; i++) {
                    finalWord.remove(10);
                }
            }
            //取出词用于LDA模型
            for (Keyword w : finalWord) {
                wordList.add(w.getName());
            }
        }

        //LDA语料库
        Corpus corpus = new Corpus();
        //添加到语料库中
        corpus.addDocument(wordList);

        // 2. Create a LDA sampler（构建LDA模型）
        LdaGibbsSampler ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
        // 3. Train it（训练主题）
        ldaGibbsSampler.gibbs(6);
        // 4. The phi matrix is a LDA model, you can use LdaUtil to explain it.
        double[][] phi = ldaGibbsSampler.getPhi();
        //获取最相关的10个词
        Map<String, Double>[] topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), 5);

        List<TopicKeyword> topicKeywords = new ArrayList<>();
        for(Map<String, Double> map:topicMap){
            String strss = "";
            int i =0;
            TopicKeyword topicKeyword = new TopicKeyword();
            for(Map.Entry<String, Double> m:map.entrySet()){
                if(i==0){
                    topicKeyword.setTopic(m.getKey());
                }
                i++;
                String str = m.getKey()+",";
                strss  += str;
            }
            topicKeyword.setKeyword(strss);
            topicKeywords.add(topicKeyword);
        }


        return topicKeywords;
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
               // System.out.println(split[i-1]);
                InventorLink link = new InventorLink();
                link.setSource(split[i-1]);
                link.setTarget(split[i]);
                link.setName("合作");
                links.add(link);
            }

        }
       // System.err.println(patents.size());
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
        //获取发明人信息
        Inventor inventer = inventorService.findUserByUniversityAndName("%"+university+"%", "%"+inventor+"%");
        //获取发明人专利信息
        List<Patent> patents = patentService.inventorDetail("%" + university + "%", "%" + inventor + "%");
        //对发明人专利进行分词处理
        List<List<Keyword>> patentTitles = new ArrayList<>();
        //分词器
        TFIDFAnalyzer tfidfAnalyzer = new TFIDFAnalyzer();

        for(Patent p:patents){
            String patentTitle1 = p.getPatentTitle();
            List<Keyword> list = tfidfAnalyzer.analyze(patentTitle1, 10);
            patentTitles.add(list);
        }

        Map<String,Double> hashMap = new HashMap<>();
        for(List<Keyword> keywords:patentTitles){
            for(Keyword keyword:keywords){
                if(keyword.getName()!=null || "".equals(keyword.getName())){
                    if(hashMap.containsKey(keyword.getName())){
                        hashMap.put(keyword.getName(),keyword.getTfidfvalue()+hashMap.get(keyword.getName()));
                    }else{
                        hashMap.put(keyword.getName(),keyword.getTfidfvalue());
                    }
                }
            }
        }
        //装入关键词
        List<InventorTopic> topic = new ArrayList<>();
        for(Map.Entry<String,Double> m:hashMap.entrySet()){
            InventorTopic t = new InventorTopic();
            t.setName(m.getKey());
            topic.add(t);
        }
        //处理关键词连接关系
        List<TopicLink> links =new ArrayList<>();
        for(List<Keyword> keywords:patentTitles){
            for(int i = 1;i<keywords.size();i++){
                TopicLink link = new TopicLink();
                link.setSource(keywords.get(i-1).getName());
                link.setTarget(keywords.get(i).getName());
                link.setValue(keywords.get(i-1).getTfidfvalue()+keywords.get(i).getTfidfvalue());
                links.add(link);
            }
        }


        Map<String,Object> map= new HashMap<>();
        map.put("inventor",inventer);
        map.put("patent",patents);
        map.put("topic",topic);
        map.put("link",links);
        return map;
    }
    /**
     * 热力图绘制
     */
    @PostMapping("/heatphoto")
    public Object heat(String keyword){
        //根据关键词检索专利
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
        //将所有大学组成List集合
        Map<String,Integer> hashMap =new HashMap<>();
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
        }
        //需要返回前端的数据
        List<List<Double>> mapData;
        mapData = new ArrayList<>();
        //设置大学的经纬度、以及专利数量
        for(int i=0;i<newPerson.size();i++){
            List<Double> item = new ArrayList<>();    //每个数据点的数据
            Map<String, Double> map = new HashMap<>();  //获取每个地址转换后的经纬度
            System.err.println("location:"+newPerson.get(i).getName());
            map = LngAndLatUtil.getLngAndLat(newPerson.get(i).getName());
            item.add(map.get("lng"));    //经度
            item.add(map.get("lat"));    // 纬度
            item.add(Double.valueOf(newPerson.get(i).getNumber()));             //值
            mapData.add(item);
        }
        return mapData;
    }
}