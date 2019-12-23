package com.how2java.tmall.pojo;

import javax.persistence.Column;

public class SimilarPatent implements Comparable<SimilarPatent>{

    String patentInventor;
    String patentDesc;
    String patentTitle;
    String applyPerson;
    Double similarValue;

    public String getPatentInventor() {
        return patentInventor;
    }

    public void setPatentInventor(String patentInventor) {
        this.patentInventor = patentInventor;
    }

    public String getPatentDesc() {
        return patentDesc;
    }

    public void setPatentDesc(String patentDesc) {
        this.patentDesc = patentDesc;
    }

    public String getPatentTitle() {
        return patentTitle;
    }

    public void setPatentTitle(String patentTitle) {
        this.patentTitle = patentTitle;
    }

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }

    public Double getSimilarValue() {
        return similarValue;
    }

    public void setSimilarValue(Double similarValue) {
        this.similarValue = similarValue;
    }

    @Override
    public String toString() {
        return "SimilarPatent{" +
                "patentInventor='" + patentInventor + '\'' +
                ", patentDesc='" + patentDesc + '\'' +
                ", patentTitle='" + patentTitle + '\'' +
                ", applyPerson='" + applyPerson + '\'' +
                ", similarValue=" + similarValue +
                '}';
    }

    @Override
    public int compareTo(SimilarPatent o) {

        if(this.getSimilarValue()<o.getSimilarValue()){
            return 1;
        }else if(this.getSimilarValue()>o.getSimilarValue()){
            return -1;
        }else{
            return 0;
        }
    }
}
