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
@Table(name = "agency")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class Agency {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Transient
	List<PatentBroker> patentBrokers;
	
	private String agentName;
	private String agentAddress;
	private String agentPhone;
	private String agentDesc;
	private int employCount;
	
	public List<PatentBroker> getPatentBrokers() {
		return patentBrokers;
	}

	public void setPatentBrokers(List<PatentBroker> patentBrokers) {
		this.patentBrokers = patentBrokers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentAddress() {
		return agentAddress;
	}

	public void setAgentAddress(String agentAddress) {
		this.agentAddress = agentAddress;
	}

	public String getAgentPhone() {
		return agentPhone;
	}

	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}

	public String getAgentDesc() {
		return agentDesc;
	}

	public void setAgentDesc(String agentDesc) {
		this.agentDesc = agentDesc;
	}

	public int getEmployCount() {
		return employCount;
	}

	public void setEmployCount(int employCount) {
		this.employCount = employCount;
	}

	@Override
	public String toString() {
		return "Agency [id=" + id + ", agentName=" + agentName + ", agentAddress=" + agentAddress + ", agentPhone="
				+ agentPhone + ", agentDesc=" + agentDesc + ", employCount=" + employCount + "]";
	}

}
