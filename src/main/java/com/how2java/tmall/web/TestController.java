package com.how2java.tmall.web;

import com.how2java.tmall.util.LngAndLatUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @PostMapping("/testheatphoto")
    public Object heat(){
        //需要绘图的地址
        String[] address = {"西安电子科技大学",
                "广东工业大学",
                "东北大学",
                "南京邮电大学",
                "北京工业大学",
                "中国地质大学(武汉)",
                "哈尔滨工程大学",
                "上海交通大学",
                "北京航空航天大学",
                "浙江大学",
                "清华大学",
                "天津大学",
                "重庆邮电大学",
                "天津理工大学",
                "暨南大学",
                "电子科技大学",
                "湘潭大学",
                "成都理工大学",
                "东华大学",
                "曲阜师范大学",
                "哈尔滨理工大学",
                "北京邮电大学",
                "北京科技大学",
                "江西理工大学",
                "北京理工大学",
                "华北电力大学(保定)",
                "浙江工商大学",
                "华东师范大学",
                "中国人民解放军国防科技大学",
                "华南理工大学",
                "华中科技大学",
                "南京大学"};
        Double[] values ={10.0,
                10.0,
                8.0,
                6.0,
                5.0,
                5.0,
                5.0,
                4.0,
                4.0,
                4.0,
                3.0,
                3.0,
                3.0,
                3.0,
                3.0,
                3.0,
                3.0,
                2.0,
                2.0,
                2.0,
                2.0,
                2.0,
                2.0,
                2.0,
                2.0,
                2.0,
                2.0,
                2.0,
                2.0,
                2.0,
                2.0,
                2.0};
        List<List<Double>> mapData;   //需要返回前端的数据
        mapData = new ArrayList<>();
        //for (String addr : address) {
        for(int i =0;i<address.length;i++){
            List<Double> item = new ArrayList<>();    //每个数据点的数据
            Map<String, Double> map = new HashMap<>();  //获取每个地址转换后的经纬度
            map = LngAndLatUtil.getLngAndLat(address[i]);
            item.add(map.get("lng"));    //经度
            item.add(map.get("lat"));    // 纬度
            item.add(values[i]);             //值
            mapData.add(item);
        }
/*        for(int i=0;i<address.length;i++){
            List<Double> item = new ArrayList<>();    //每个数据点的数据
            Map<String, Double> map = new HashMap<>();  //获取每个地址转换后的经纬度
            map = LngAndLatUtil.getLngAndLat(address[i]);
            item.add(map.get("lng"));    //经度
            item.add(map.get("lat"));    // 纬度
            item.add(500.0+100*i);             //值
            mapData.add(item);
        }*/
        return mapData;
    }
}
