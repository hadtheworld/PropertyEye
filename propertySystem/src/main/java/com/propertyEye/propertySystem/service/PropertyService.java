package com.propertyEye.propertySystem.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.propertyEye.propertySystem.Exceptions.WrongIdException;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Users;

public interface PropertyService {
	public Property addProperty(long sellerId,Property property) throws WrongIdException;
	public Users addNewBuyers(long propertyId, long buyerId) throws WrongIdException;
	public Property getProperty(long propertyId) throws NoSuchElementException;
	public List<Property> getAllProeperties();
	public void deleteProperty(long propertyId) throws WrongIdException;
}
