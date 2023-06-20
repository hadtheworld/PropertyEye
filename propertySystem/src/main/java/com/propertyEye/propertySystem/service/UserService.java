package com.propertyEye.propertySystem.service;

import java.util.List;

import com.propertyEye.propertySystem.Exceptions.WrongIdException;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Users;

public interface UserService {
	public List<Users> getUsers();
	public Users getUser(long userId);
	public Users addUsers(Users user);
	public Users updateUsers(Users user);
	public void deleteUser(long userId) throws WrongIdException;
	public List<Property> getPropertyBuyer(Long buyerId);
	
}
