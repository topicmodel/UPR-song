package com.how2java.tmall.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "productassess")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class ProductAssess {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	@JoinColumn(name="pid")
	private Product product;

	private float marketapply;
	private float applyscale;
	private float marketshare;
	private float compete;
	private float policyapply;
	private float ability;
	private float need;
	private float score;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public float getMarketapply() {
		return marketapply;
	}
	public void setMarketapply(float marketapply) {
		this.marketapply = marketapply;
	}
	public float getApplyscale() {
		return applyscale;
	}
	public void setApplyscale(float applyscale) {
		this.applyscale = applyscale;
	}
	public float getMarketshare() {
		return marketshare;
	}
	public void setMarketshare(float marketshare) {
		this.marketshare = marketshare;
	}
	public float getCompete() {
		return compete;
	}
	public void setCompete(float compete) {
		this.compete = compete;
	}
	public float getPolicyapply() {
		return policyapply;
	}
	public void setPolicyapply(float policyapply) {
		this.policyapply = policyapply;
	}
	public float getAbility() {
		return ability;
	}
	public void setAbility(float ability) {
		this.ability = ability;
	}
	public float getNeed() {
		return need;
	}
	public void setNeed(float need) {
		this.need = need;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "ProductAssess [id=" + id + ", marketapply=" + marketapply + ", applyscale=" + applyscale
				+ ", marketshare=" + marketshare + ", compete=" + compete + ", policyapply=" + policyapply
				+ ", ability=" + ability + ", need=" + need + ", score=" + score + "]";
	}
}
