package com.propertyEye.propertySystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propertyEye.propertySystem.entities.Property;

@Repository
public interface PropertyDao extends JpaRepository<Property, Long>{
//	public List<Users> findBuyersByProperty(Property property);
	public List<Property> findPropertysByBuyersUserId(Long buyerId);
	public List<Property> findBySellerSellerId(Long sellerId);
	public Property findByPropId(Long propertyId);
	
}
