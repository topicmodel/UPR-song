package com.how2java.tmall.test;
  
import com.how2java.tmall.util.MySimHash;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
  
public class TestSimilar {
  
    public static void main(String args[]){
        String s1 = "安卓手机解锁程序";
        String s2="一件用于手机解锁的技术";
        MySimHash hash1 = new MySimHash(s1, 64);
        MySimHash hash2 = new MySimHash(s2, 64);
        System.out.println( hash1.getSemblance(hash2) );
    }
  
}