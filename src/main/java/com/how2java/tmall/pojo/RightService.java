package com.how2java.tmall.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "rightservice")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })

public class RightService {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	private String serviceName;
	private String serviceType;
	private int servicePrice;
	private String serviceDesc;
	private Date createDate;

	@Transient
	private RightServiceImage firstRightServiceImage;
	@Transient
	private List<RightServiceImage> rigthServiceSingleImages;
	
	@ManyToOne
	@JoinColumn(name = "patentdomainid")
	private PatentDomain patentDomain;


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public RightServiceImage getFirstRightServiceImage() {
		return firstRightServiceImage;
	}

	public void setFirstRightServiceImage(RightServiceImage firstRightServiceImage) {
		this.firstRightServiceImage = firstRightServiceImage;
	}

	public List<RightServiceImage> getRigthServiceSingleImages() {
		return rigthServiceSingleImages;
	}

	public void setRigthServiceSingleImages(List<RightServiceImage> rigthServiceSingleImages) {
		this.rigthServiceSingleImages = rigthServiceSingleImages;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public int getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(int servicePrice) {
		this.servicePrice = servicePrice;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public PatentDomain getPatentDomain() {
		return patentDomain;
	}

	public void setPatentDomain(PatentDomain patentDomain) {
		this.patentDomain = patentDomain;
	}

	@Override
	public String toString() {
		return "RightService [id=" + id + ", serviceName=" + serviceName + ", serviceType=" + serviceType
				+ ", servicePrice=" + servicePrice + ", serviceDesc=" + serviceDesc + ", patentDomain=" + patentDomain
				+ "]";
	}

}
