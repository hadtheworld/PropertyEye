package com.propertyEye.propertySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propertyEye.propertySystem.Exceptions.WrongIdException;
import com.propertyEye.propertySystem.dao.PropertyDao;
import com.propertyEye.propertySystem.dao.UserDao;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Users;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private PropertyDao propertyDao;
	
	@Autowired
	private UserDao userDao;
	
	
	
	@Override
	public List<Users> getUsers() {
//		fetch all the records from the table to show
		return this.userDao.findAll();
	}
	
	
	@Override
	public Users getUser(long userId)  {
//		fetch user based on their user id and return null if not user found

		return this.userDao.findById(userId).get();
	}

	@Override
	public Users addUsers(Users user) {
//		add new user to the list of users and return the added user to the one making post request
		this.userDao.save(user);
		return user;
	}

	@Override
	public Users updateUsers(Users user) {
//		update user info the id of the user matching it
		this.userDao.save(user);
		return user;
	}

//	delete the user from the list based on the userId passed to it
	@Override
	public void deleteUser(long userId) throws WrongIdException{
		if(this.userDao.findByUserId(userId)==null) {
			throw new WrongIdException("User does not exists");
		}
		Users buyer= this.userDao.findByUserId(userId);
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
//		to get the list of property for a type of user
				return this.propertyDao.findPropertysByBuyersUserId(buyerId);
	}

}