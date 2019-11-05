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
@Table(name = "information")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class Information {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "infocid")
	private InfoCategory infoCategory;

	private String title;
	private String contentDesc;
	private Date createDate;
	
	@Transient
	private InformationImage firstInformationImage;
	@Transient
	private List<InformationImage> informationSingleImages;
	@Transient
	private List<InformationImage> informationDetailImages;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InfoCategory getInfoCategory() {
		return infoCategory;
	}

	public void setInfoCategory(InfoCategory infoCategory) {
		this.infoCategory = infoCategory;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentDesc() {
		return contentDesc;
	}

	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public InformationImage getFirstInformationImage() {
		return firstInformationImage;
	}

	public void setFirstInformationImage(InformationImage firstInformationImage) {
		this.firstInformationImage = firstInformationImage;
	}

	public List<InformationImage> getInformationSingleImages() {
		return informationSingleImages;
	}

	public void setInformationSingleImages(List<InformationImage> informationSingleImages) {
		this.informationSingleImages = informationSingleImages;
	}

	public List<InformationImage> getInformationDetailImages() {
		return informationDetailImages;
	}

	public void setInformationDetailImages(List<InformationImage> informationDetailImages) {
		this.informationDetailImages = informationDetailImages;
	}

	@Override
	public String toString() {
		return "Information [id=" + id + ", infoCategory=" + infoCategory + ", title=" + title + ", contentDesc="
				+ contentDesc + ", createDate=" + createDate + "]";
	}

}
