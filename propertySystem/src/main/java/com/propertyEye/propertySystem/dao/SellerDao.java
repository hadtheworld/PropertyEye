package com.propertyEye.propertySystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.propertyEye.propertySystem.entities.Seller;

@Repository
public interface SellerDao extends JpaRepository<Seller, Long>{
	public Seller findBySellerId(long userId);
	public Seller findBySellerEmail(String email);
	
	@Query("select s from Seller s where s.sellerEmail=?1 and s.sellerPassword=?2")
	public Seller userLogin(String emailId, String password);
}