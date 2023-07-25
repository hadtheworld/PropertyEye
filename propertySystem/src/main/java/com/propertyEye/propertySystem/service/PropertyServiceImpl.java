package com.propertyEye.propertySystem.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propertyEye.propertySystem.Exceptions.EmptyInputException;
import com.propertyEye.propertySystem.Exceptions.EmptyOutputException;
import com.propertyEye.propertySystem.dao.PropertyDao;
import com.propertyEye.propertySystem.dao.SellerDao;
import com.propertyEye.propertySystem.dao.UserDao;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Users;
import com.propertyEye.propertySystem.logger.PropertyLogger;

@Service
public class PropertyServiceImpl implements PropertyService{
	Logger logger=PropertyLogger.getLogger(PropertyService.class);
	
	@Autowired
	private PropertyDao propertyDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SellerDao sellerDao;

	@Override
	public Property addProperty(long sellerId, Property property) {
		logger.info("addProperty() is called");
//		add new property by the seller
		if(property==null) {
			logger.warn("IllegalArgumentException");
			logger.trace("occured in addProeperty() function");
			throw new IllegalArgumentException("Request Body is missing");
		}
		if(
				property.getPropName()==null && property.getPropLocation()==null && property.getPropDescription()==null
				) {
			logger.warn("IllegalArgumentException");
			logger.trace("occured in addProeperty() function");
			throw new IllegalArgumentException("Property body is missing");
		}
		if(
				property.getPropName()==null  
				|| property.getPropName().isBlank()
				|| property.getPropLocation()==null
				|| property.getPropLocation().isBlank()) {
			logger.warn("EmptyInputException");
			logger.trace("occured in addProeperty() function");
			throw new EmptyInputException("Name and location fields cannot be empty");
		}
		 
		property.setPropName(property.getPropName().trim());
		property.setPropLocation(property.getPropLocation().trim());
		property.setPropDescription(property.getPropDescription().trim());
		Property prop = this.sellerDao.findById(sellerId).map(seller -> {
		      property.setSeller(seller);
		     this.propertyDao.save(property);
		     return property;
		    }).get();
		 return prop;
		
	}

	@Override
	public Users addNewBuyers(long propertyId, long buyerId) {
		logger.info("addNewBuyers() is called");
//		add new buyer to the list of buyers in the
		Optional<Property> optionalProperty = this.propertyDao.findById(propertyId);
	    Optional<Users> optionalUser=this.userDao.findById(buyerId);
	    if (optionalProperty.isPresent() && optionalUser.isPresent()) {
	        Property property = optionalProperty.get();
	        Users buyer=optionalUser.get();
	        
	        // Add the buyer to the property's interestedBuyers list
	        property.addBuyer(buyer);
	        
	        // Save the buyer entity 
	        this.userDao.save(buyer);
	        
	        // Save the property entity
	        this.propertyDao.save(property);
	           return buyer; 
	    }
	    logger.warn("NoSuchElementException");
		logger.trace("occured in addNewBuyers() function");
	   throw new NoSuchElementException();
	    
	}

	@Override
	public Property getProperty(long propertyId) {
		logger.info("getProperty() is called");
//		get property by Id
		return this.propertyDao.findById(propertyId).get();
	}
 
	@Override
	public List<Property> getAllProeperties() {
		logger.info("getAllProeperties() is called");
//		get all the property in the table
		List<Property> properties=this.propertyDao.findAll();
		if(properties.isEmpty()) {
			logger.warn(" EmptyOutputException");
			logger.trace("occured in getAllProeperties() function");
			throw new EmptyOutputException("There exists no property in the database");
		}
		return properties;
	}

	@Override
	public void deleteProperty(long propertyId){
		logger.info("deleteProperty() is called");
//		delete property from the table
		Property property=this.propertyDao.findById(propertyId).get();
		
		List<Users> buyers= this.userDao.findUsersByInterestedPropertyPropId(propertyId);
		for(Users usr:buyers) {
			property.removeBuyer(usr.getUserId());
			this.userDao.save(usr);
		}
		this.propertyDao.save(property);
		this.propertyDao.delete(property);
	}

}
