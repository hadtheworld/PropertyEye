package com.propertyEye.propertySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propertyEye.propertySystem.Exceptions.WrongIdException;
import com.propertyEye.propertySystem.dao.PropertyDao;
import com.propertyEye.propertySystem.dao.SellerDao;
import com.propertyEye.propertySystem.dao.UserDao;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Seller;
import com.propertyEye.propertySystem.entities.Users;

@Service
public class SellerServiceImpl implements SellerService{

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
//		Get all the sellers from the table
		return this.sellerDao.findAll();
	}

	@Override
	public Seller getSeller(long sellerId) {
//		get seller from seller Id
		return this.sellerDao.findBySellerId(sellerId);
	}

	@Override
	public Seller addSeller(Seller seller) throws WrongIdException{
//		add new seller to the database
		if(this.getSeller(seller.getSellerId())!=null) {
			throw new WrongIdException("Seller Already Exists");
		}
		this.sellerDao.save(seller);
		return seller;
	}

	@Override
	public void deleteSeller(long sellerId) throws WrongIdException{
//		delete seller from the database
		if(this.getSeller(sellerId)==null) {
			throw new WrongIdException("Seller Does Not Exists");
		}
		List<Property> properties=this.getProperties(sellerId);
		for(Property prop:properties) {
			this.propertyService.deleteProperty(prop.getPropId());
		}
		this.sellerDao.deleteById(sellerId);
	}

	@Override
	public List<Users> getBuyers(Long sellerId,Long propId) {
//		get the list of buyers
		if(this.propertyDao.findByPropId(propId)==null) {
			return null;
		}
		if(this.propertyDao.findByPropId(propId).getSeller().getSellerId()==sellerId) {
		List<Users> buyers = this.userDao.findUsersByInterestedPropertyPropId(propId);
		return buyers;
		}
		return null;
	}

	@Override
	public List<Property> getProperties(long sellerId) throws WrongIdException{
//		get all the property of seller
		if(this.getSeller(sellerId)==null) {
			throw new WrongIdException("Seller Does Not Exists");
		}
		return this.propertyDao.findBySellerSellerId(sellerId);
	}
	
	

}
