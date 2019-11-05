package com.how2java.tmall.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "patentbroker")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class PatentBroker {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "agencyid")
	private Agency agency;
	
	@ManyToOne
	@JoinColumn(name="domainid")
	private PatentDomain patentDomain;
	
	private String brokerName;
	private String brokerDomain;
	private String brokerDesc;
	private String qqNumber;
	
	
	public PatentDomain getPatentDomain() {
		return patentDomain;
	}

	public void setPatentDomain(PatentDomain patentDomain) {
		this.patentDomain = patentDomain;
	}

	public String getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public String getBrokerDomain() {
		return brokerDomain;
	}

	public void setBrokerDomain(String brokerDomain) {
		this.brokerDomain = brokerDomain;
	}

	public String getBrokerDesc() {
		return brokerDesc;
	}

	public void setBrokerDesc(String brokerDesc) {
		this.brokerDesc = brokerDesc;
	}


	@Override
	public String toString() {
		return "PatentBroker [id=" + id + ", agency=" + agency + ", brokerName=" + brokerName + ", brokerDomain="
				+ brokerDomain + ", brokerDesc=" + brokerDesc+ "]";
	}

}
