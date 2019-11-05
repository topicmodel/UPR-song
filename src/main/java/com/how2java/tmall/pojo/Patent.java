package com.how2java.tmall.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table(name = "patent")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class Patent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "patentagency")
    private String patenAgency;
    @Column(name = "patentipc")
    private String patentIpc;
    @Column(name = "publicnum")
    private String publicNum;
    @Column(name = "patentinventor")
    private String patentInventor;
    @Column(name = "countrycode")
    private String countryCode;
    @Column(name = "patentdesc")
    private String patentDesc;
    @Column(name = "patenttitle")
    private String patentTitle;
    @Column(name = "applyperson")
    private String applyPerson;
    @Column(name = "applynum")
    private String applyNum;
    @Column(name = "pagenum")
    private String pageNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatenAgency() {
        return patenAgency;
    }

    public void setPatenAgency(String patenAgency) {
        this.patenAgency = patenAgency;
    }

    public String getPatentIpc() {
        return patentIpc;
    }

    public void setPatentIpc(String patentIpc) {
        this.patentIpc = patentIpc;
    }

    public String getPublicNum() {
        return publicNum;
    }

    public void setPublicNum(String publicNum) {
        this.publicNum = publicNum;
    }

    public String getPatentInventor() {
        return patentInventor;
    }

    public void setPatentInventor(String patentInventor) {
        this.patentInventor = patentInventor;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public String getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(String applyNum) {
        this.applyNum = applyNum;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return "Patent{" +
                "id=" + id +
                ", patenAgency='" + patenAgency + '\'' +
                ", patentIpc='" + patentIpc + '\'' +
                ", publicNum='" + publicNum + '\'' +
                ", patentInventor='" + patentInventor + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", patentDesc='" + patentDesc + '\'' +
                ", patentTitle='" + patentTitle + '\'' +
                ", applyPerson='" + applyPerson + '\'' +
                ", applyNum='" + applyNum + '\'' +
                ", pageNum='" + pageNum + '\'' +
                '}';
    }
}
