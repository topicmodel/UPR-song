package com.how2java.tmall.pojo;

public class PatentInventor implements Comparable<PatentInventor>{
    String inventorName;
    Integer inventorNum;

    public String getInventorName() {
        return inventorName;
    }

    public void setInventorName(String inventorName) {
        this.inventorName = inventorName;
    }

    public Integer getInventorNum() {
        return inventorNum;
    }

    public void setInventorNum(Integer inventorNum) {
        this.inventorNum = inventorNum;
    }

    @Override
    public String toString() {
        return "PatentInventor{" +
                "inventorName='" + inventorName + '\'' +
                ", inventorNum=" + inventorNum +
                '}';
    }

    @Override
    public int compareTo(PatentInventor p) {
        return p.getInventorNum()-this.getInventorNum();
    }
}
