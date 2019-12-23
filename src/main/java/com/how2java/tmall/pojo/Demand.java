package com.how2java.tmall.pojo;

public class Demand {
    String name;
    String Desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    @Override
    public String toString() {
        return "Demand{" +
                "name='" + name + '\'' +
                ", Desc='" + Desc + '\'' +
                '}';
    }
}
