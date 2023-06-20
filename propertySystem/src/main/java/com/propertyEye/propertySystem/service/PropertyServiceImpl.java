package com.propertyEye.propertySystem.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propertyEye.propertySystem.Exceptions.WrongIdException;
import com.propertyEye.propertySystem.dao.PropertyDao;
import com.propertyEye.propertySystem.dao.SellerDao;
import com.propertyEye.propertySystem.dao.UserDao;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Users;

@Service
public class PropertyServiceImpl implements PropertyService{
	
	@Autowired
	private PropertyDao propertyDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SellerDao sellerDao;

	@Override
	public Property addProperty(long sellerId, Property property) throws WrongIdException {
//		add new property by the seller
		if(this.propertyDao.findByPropId(property.getPropId())!=null) {
			throw new WrongIdException("Property Already Exists");
		}
		
		Property prop = this.sellerDao.findById(sellerId).map(seller -> {
		      property.setSeller(seller);
		     this.propertyDao.save(property);
		     return property;
		    }).get();
		 return prop;
	}

	@Override
	public Users addNewBuyers(long propertyId, long buyerId) throws WrongIdException {
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
	    
	    throw new WrongIdException("Prosuct or user does not Exists");
	}

	@Override
	public Property getProperty(long propertyId) throws NoSuchElementException {
//		get property by Id
		return this.propertyDao.findByPropId(propertyId);
	}

	@Override
	public List<Property> getAllProeperties() {
//		get all the property in the table
		return this.propertyDao.findAll();
	}

	@Override
	public void deleteProperty(long propertyId) throws WrongIdException {
//		delete property from the table
		Property property=this.propertyDao.findByPropId(propertyId);
		if(property==null) {
			throw new WrongIdException("Property Does Not Exists");
		}
		
		List<Users> buyers= this.userDao.findUsersByInterestedPropertyPropId(propertyId);
		for(Users usr:buyers) {
			property.removeBuyer(usr.getUserId());
			this.userDao.save(usr);
		}
		this.propertyDao.save(property);
		this.propertyDao.delete(property);
	}

}
