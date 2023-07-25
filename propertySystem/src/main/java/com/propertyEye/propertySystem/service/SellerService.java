package com.propertyEye.propertySystem.service;

import java.util.List;

import com.propertyEye.propertySystem.entities.LoginModel;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Seller;
import com.propertyEye.propertySystem.entities.Users;

public interface SellerService {
	public List<Seller> getAllSellers();
	public Seller getSeller(long sellerId);
	public Seller addSeller(Seller seller);
	public void deleteSeller(long sellerId);
	public List<Users> getBuyers(Long sellerId,Long propId);
	public List<Property> getProperties(long sellerId);
	public Seller userLogin(LoginModel model);
}
