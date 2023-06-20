package com.propertyEye.propertySystem.entities;



import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Users {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long userId;
	private String userName;
	private String userEmail;
	private String userPassword;
	private long userPhone;
	private String userAddress;
	private final boolean userType=false;
	
	@JsonIgnore
	@ManyToMany(fetch= FetchType.EAGER,mappedBy="buyers")
	private List<Property> interestedProperty = new ArrayList<Property>();
	
	
	public List<Property> getInterestedProperty() {
		return interestedProperty;
	}
	public void setInterestedProperty(List<Property> interstedProperty) {
		this.interestedProperty = interstedProperty;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public long getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public boolean isUserType() {
		return userType;
	}

	
	
	@Override
	public String toString() {
		return "Users [userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail + ", userPassword="
				+ userPassword + ", userPhone=" + userPhone + ", userAddress=" + userAddress + ", userType=" + userType
				+ ", interestedProperty=" + interestedProperty + "]";
	}
	
}
