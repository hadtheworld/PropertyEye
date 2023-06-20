package com.propertyEye.propertySystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propertyEye.propertySystem.entities.Users;

@Repository
public interface UserDao extends JpaRepository<Users, Long>{
//	public List<Property> findListedPropertyBySeller(Users seller);
//	public List<Property> findInterestedPropertyByBuyer(Users buyer);
	public Users findByUserId(long userId);
	public Users findByUserName(String userName);
	public List<Users> findUsersByInterestedPropertyPropId(Long propertyId);
}
