package com.propertyEye.propertySystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propertyEye.propertySystem.entities.Seller;

@Repository
public interface SellerDao extends JpaRepository<Seller, Long>{
	public Seller findBySellerId(long userId);
	public Seller findBySellerName(String userName);
}
