package com.propertyEye.propertySystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.propertyEye.propertySystem.entities.Users;

@Repository
public interface UserDao extends JpaRepository<Users, Long>{
//	public List<Property> findListedPropertyBySeller(Users seller);
//	public List<Property> findInterestedPropertyByBuyer(Users buyer);
	public Users findByUserId(long userId);
	public Users findByUserEmail(String userEmail);
	public List<Users> findUsersByInterestedPropertyPropId(Long propertyId);
	
	@Query("select u from Users u where u.userEmail=?1 and u.userPassword=?2")
	public Users userLogin(String emailId, String password);
}
