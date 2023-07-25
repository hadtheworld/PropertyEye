package com.propertyEye.propertySystem.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propertyEye.propertySystem.Exceptions.EmptyInputException;
import com.propertyEye.propertySystem.Exceptions.EmptyOutputException;
import com.propertyEye.propertySystem.Exceptions.UniqueValueException;
import com.propertyEye.propertySystem.dao.PropertyDao;
import com.propertyEye.propertySystem.dao.UserDao;
import com.propertyEye.propertySystem.entities.LoginModel;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Users;
import com.propertyEye.propertySystem.logger.PropertyLogger;

@Service
public class UserServiceImpl implements UserService{
	Logger logger=PropertyLogger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private PropertyDao propertyDao;
	
	@Autowired
	private UserDao userDao;
	
	
	
	@Override
	public List<Users> getUsers() {
		logger.info("getUsers() is called");
//		fetch all the records from the table to show
		List<Users> user=this.userDao.findAll();
		if(user.isEmpty()) {
			logger.warn("EmptyOutputException");
			logger.trace("occured in getUsers() function");
			throw new EmptyOutputException("No user in the database");
		}
		return user;
	}
	
	
	@Override
	public Users getUser(long userId)  {
		logger.info("getUser() is called");
//		fetch user based on their user id and return null if not user found

		return this.userDao.findById(userId).get();
	}

	@Override
	public Users addUsers(Users user) {
		logger.info("addUsers() is called");
//		add new user to the list of users and return the added user to the one making post request
		if(user == null) {
			logger.warn("IllegalArgumentException");
			logger.trace("occured in addUsers() function");
			throw new IllegalArgumentException("Request Body is missing");
		}
		if ((user.getUserName()==null && user.getUserPassword()==null &&
	            user.getUserEmail()==null && user.getUserPhone() == null)) {
			logger.warn("IllegalArgumentException");
			logger.trace("occured in addUsers() function");
	        throw new IllegalArgumentException("User details are missing");
	    }
		if(user.getUserName()==null || user.getUserName().isBlank()
				 || user.getUserPassword()==null || user.getUserPassword().isBlank()
				|| user.getUserEmail()==null || user.getUserEmail().isBlank() 
				|| user.getUserPhone()==null || user.getUserPhone().isBlank() ) {
			logger.warn("EmptyInputException");
			logger.trace("occured in addUsers() function");
			throw new EmptyInputException("name, Email, Phone and password cannot be empty");
		}
		if(this.userDao.findByUserEmail(user.getUserEmail())!=null) {
			logger.warn("UniqueValueException");
			logger.trace("occured in addUsers() function");
			throw new UniqueValueException("user email already in use");
		}
		this.userDao.save(user);
		return user;
	}


//	delete the user from the list based on the userId passed to it
	@Override
	public void deleteUser(long userId){
		logger.info("deleteUser() is called");
		
		Users buyer= this.userDao.findById(userId).get();
		List<Property> properties=this.getPropertyBuyer(userId);
		for(Property prop:properties) {
			prop.removeBuyer(userId);
			this.propertyDao.save(prop);
		}
		this.userDao.save(buyer);
		this.userDao.deleteById(userId);
		
	}

	@Override
	public List<Property> getPropertyBuyer(Long buyerId) {
		logger.info("getPropertyBuyer() is called");
//		to get the list of property for a type of user
		if(!this.userDao.existsById(buyerId)) {
			logger.warn("NoSuchElementException");
			logger.trace("occured in getPropertyBuyer() function");
			throw new NoSuchElementException();
		}
				return this.propertyDao.findPropertysByBuyersUserId(buyerId);
	}


	@Override
	public Users userLogin(LoginModel model) {
		logger.info("userLogin() is called");
//		login to the user
		Users user=this.userDao.userLogin(model.getEmailId(),model.getPassword());
		if(user==null) {
			logger.warn("EmptyOutputException");
			logger.trace("occured in userLogin() function");
			throw new EmptyOutputException("user not found");
		}
		return user;
	}

}