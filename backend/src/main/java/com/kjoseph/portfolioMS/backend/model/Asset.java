package com.kjoseph.portfolioMS.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "assets")
public class Asset {
	@Id
	@Column(name = "asset_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long accountId;
	private long portfolioId;
	private String assetType;
	private String ticker;
	private int amount;
	private double avgPurchasePrice;

	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	
	public long getPortfolioId() {
		return portfolioId;
	}
	public void setPortfolioId(long portfolioId) {
		this.portfolioId = portfolioId;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getAvgPurchasePrice() {
		return avgPurchasePrice;
	}
	public void setAvgPurchasePrice(double avgPurchasePrice) {
		this.avgPurchasePrice = avgPurchasePrice;
	}
	
	

}