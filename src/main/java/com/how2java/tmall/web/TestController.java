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
    /*@PostMapping("/heatphoto")
    public Object heat(){
        String[] address = {"武汉大学经济管理学院", "华侨大学计算机学院", "厦门大学计算机学院"};  //需要绘图的地址
        List<List<Double>> mapData;   //需要返回前端的数据
        mapData = new ArrayList<>();
        *//*for (String addr : address) {
            List<Double> item = new ArrayList<>();    //每个数据点的数据
            Map<String, Double> map = new HashMap<>();  //获取每个地址转换后的经纬度
            map = LngAndLatUtil.getLngAndLat(addr);
            item.add(map.get("lng"));    //经度
            item.add(map.get("lat"));    // 纬度
            item.add(500.0);             //值
            mapData.add(item);
        }*//*
        for(int i=0;i<address.length;i++){
            List<Double> item = new ArrayList<>();    //每个数据点的数据
            Map<String, Double> map = new HashMap<>();  //获取每个地址转换后的经纬度
            map = LngAndLatUtil.getLngAndLat(address[i]);
            item.add(map.get("lng"));    //经度
            item.add(map.get("lat"));    // 纬度
            item.add(500.0+100*i);             //值
            mapData.add(item);
        }
        return mapData;
    }*/
}
