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
@Table(name = "patentdomain")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class PatentDomain {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	private String domainName;

	@Transient
	private List<PatentBroker> brokers;
	
	@Transient
	private List<RightService> services;
	
	public List<RightService> getServices() {
		return services;
	}
	
	public void setServices(List<RightService> services) {
		this.services = services;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public List<PatentBroker> getBrokers() {
		return brokers;
	}

	public void setBrokers(List<PatentBroker> brokers) {
		this.brokers = brokers;
	}

	@Override
	public String toString() {
		return "PatentDomain [id=" + id + ", domainName=" + domainName + ", brokers=" + brokers + "]";
	}

}
