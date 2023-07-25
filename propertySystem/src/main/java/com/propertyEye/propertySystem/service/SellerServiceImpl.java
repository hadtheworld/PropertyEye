package com.propertyEye.propertySystem.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propertyEye.propertySystem.Exceptions.EmptyInputException;
import com.propertyEye.propertySystem.Exceptions.EmptyOutputException;
import com.propertyEye.propertySystem.Exceptions.UniqueValueException;
import com.propertyEye.propertySystem.dao.PropertyDao;
import com.propertyEye.propertySystem.dao.SellerDao;
import com.propertyEye.propertySystem.dao.UserDao;
import com.propertyEye.propertySystem.entities.LoginModel;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Seller;
import com.propertyEye.propertySystem.entities.Users;
import com.propertyEye.propertySystem.logger.PropertyLogger;

@Service
public class SellerServiceImpl implements SellerService{
	Logger logger=PropertyLogger.getLogger(SellerServiceImpl.class);

	@Autowired
	private SellerDao sellerDao;
	
	@Autowired
	private PropertyDao propertyDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PropertyServiceImpl propertyService;
	
	@Override
	public List<Seller> getAllSellers() {
		logger.info("getAllSellers() is called");
//		Get all the sellers from the table
		List<Seller> sellers=this.sellerDao.findAll();
		if(sellers.isEmpty()) {
			logger.warn("EmptyOutputException");
			logger.trace("occured in getAllSellers() function");
			throw new EmptyOutputException("No seller in the database");
		}
		return sellers;
	}

	@Override
	public Seller getSeller(long sellerId) {
		logger.info("getAllSellers() is called");
//		get seller from seller Id
		return this.sellerDao.findById(sellerId).get();
	}

	@Override
	public Seller addSeller(Seller seller){
		logger.info("getAllSellers() is called");
//		add new seller to the database
		if(seller == null) {
			logger.warn("IllegalArgumentException");
			logger.trace("occured in addSeller() function");
			throw new IllegalArgumentException("Request Body is missing");
		}
		if ((seller.getSellerName()==null && seller.getSellerPassword()==null &&
	            seller.getSellerEmail()==null && seller.getSellerPhone() == null)) {
			logger.warn("IllegalArgumentException");
			logger.trace("occured in addSeller() function");
	        throw new IllegalArgumentException("Seller details are missing");
	    }
		if( seller.getSellerName()==null || seller.getSellerName().isBlank()
				 || seller.getSellerPassword()==null || seller.getSellerPassword().isBlank()
				|| seller.getSellerEmail()==null || seller.getSellerEmail().isBlank() 
				|| seller.getSellerPhone()==null || seller.getSellerPhone().isBlank() ) {
			logger.warn("EmptyInputException");
			logger.trace("occured in addSeller() function");
			throw new EmptyInputException("name, Email, Phone and password cannot be empty");
		}
		
		if(this.sellerDao.findBySellerEmail(seller.getSellerEmail())!=null) {
			logger.warn("UniqueValueException");
			logger.trace("occured in addSeller() function");
			throw new UniqueValueException("seller email already in use");
		}
		this.sellerDao.save(seller);
		return seller;
	}

	@Override
	public void deleteSeller(long sellerId){
		logger.info("getAllSellers() is called");
//		delete seller from the database
		List<Property> properties=this.getProperties(sellerId);
		for(Property prop:properties) {
			this.propertyService.deleteProperty(prop.getPropId());
		}
		this.sellerDao.deleteById(sellerId);
	}

	@Override
	public List<Users> getBuyers(Long sellerId,Long propId) {
		logger.info("getAllSellers() is called");
//		get the list of buyers
		if(!this.sellerDao.existsById(sellerId)) {
			logger.warn("NoSuchElementException");
			logger.trace("occured in getBuyers() function");
			throw new NoSuchElementException(); 
		}
		if(this.propertyDao.findById(propId).get().getSeller().getSellerId()==sellerId) {
		List<Users> buyers = this.userDao.findUsersByInterestedPropertyPropId(propId);
		return buyers;
		}
		throw new IllegalArgumentException("Property Does not belong to the seller");
	}

	@Override
	public List<Property> getProperties(long sellerId){
		logger.info("getAllSellers() is called");
//		get all the property of seller
		if(!this.sellerDao.existsById(sellerId)) {
			logger.warn("NoSuchElementException");
			logger.trace("occured in getProperties() function");
			throw new NoSuchElementException();
		}
		return this.propertyDao.findBySellerSellerId(sellerId);
	}

	@Override
	public Seller userLogin(LoginModel model) {
		logger.info("getAllSellers() is called");
//		seller login
		Seller seller=this.sellerDao.userLogin(model.getEmailId(),model.getPassword());
		if(seller==null) {
			logger.warn("EmptyOutputException");
			logger.trace("occured in userLogin() function");
			throw new EmptyOutputException("seller not found");
		}
		return seller;
	}
	
	

}
