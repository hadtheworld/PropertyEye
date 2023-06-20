package com.propertyEye.propertySystem.service;

import java.util.List;

import com.propertyEye.propertySystem.Exceptions.WrongIdException;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Seller;
import com.propertyEye.propertySystem.entities.Users;

public interface SellerService {
	public List<Seller> getAllSellers();
	public Seller getSeller(long sellerId);
	public Seller addSeller(Seller seller) throws WrongIdException;
	public void deleteSeller(long sellerId) throws WrongIdException;
	public List<Users> getBuyers(Long sellerId,Long propId);
	public List<Property> getProperties(long sellerId) throws WrongIdException;
}
