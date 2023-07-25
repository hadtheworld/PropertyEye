package com.propertyEye.propertySystem.service;

import java.util.List;

import com.propertyEye.propertySystem.entities.LoginModel;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Users;

public interface UserService {
	public List<Users> getUsers();
	public Users getUser(long userId);
	public Users addUsers(Users user);
	public void deleteUser(long userId);
	public List<Property> getPropertyBuyer(Long buyerId);
	public Users userLogin(LoginModel model);
	
}
