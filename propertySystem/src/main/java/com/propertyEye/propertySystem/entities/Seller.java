package com.propertyEye.propertySystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Seller {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long sellerId;
	private String sellerName;
	private String sellerEmail;
	private String sellerPassword;
	private long sellerPhone;
	private String sellerAddress;
	private final boolean sellerType=true;
	
	
	public Seller() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Seller(long sellerId, String sellerName, String sellerEmail, String sellerPassword, long sellerPhone,
			String sellerAddress) {
		super();
		this.sellerId = sellerId;
		this.sellerName = sellerName;
		this.sellerEmail = sellerEmail;
		this.sellerPassword = sellerPassword;
		this.sellerPhone = sellerPhone;
		this.sellerAddress = sellerAddress;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerEmail() {
		return sellerEmail;
	}
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}
	public String getSellerPassword() {
		return sellerPassword;
	}
	public void setSellerPassword(String sellerPassword) {
		this.sellerPassword = sellerPassword;
	}
	public long getSellerPhone() {
		return sellerPhone;
	}
	public void setSellerPhone(long sellerPhone) {
		this.sellerPhone = sellerPhone;
	}
	public String getSellerAddress() {
		return sellerAddress;
	}
	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}
	public boolean isSellerType() {
		return sellerType;
	}
	
}
