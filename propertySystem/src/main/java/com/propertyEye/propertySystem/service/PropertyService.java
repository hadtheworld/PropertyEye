package com.propertyEye.propertySystem.service;

import java.util.List;

import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Users;

public interface PropertyService {
	public Property addProperty(long sellerId,Property property);
	public Users addNewBuyers(long propertyId, long buyerId);
	public Property getProperty(long propertyId);
	public List<Property> getAllProeperties();
	public void deleteProperty(long propertyId);
}
