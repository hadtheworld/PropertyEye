package com.propertyEye.propertySystem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.propertyEye.propertySystem.entities.LoginModel;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Seller;
import com.propertyEye.propertySystem.entities.Users;
import com.propertyEye.propertySystem.logger.PropertyLogger;
import com.propertyEye.propertySystem.service.SellerService;

@CrossOrigin
@RestController
@RequestMapping("/seller")
public class SellerController {
	
	Logger logger=PropertyLogger.getLogger(LoginController.class);
	
	@Autowired
	private SellerService sellerService;
	
	@GetMapping
	public ResponseEntity<List<Seller>> getAllSeller(){
		logger.info("getAllSeller is called");
		return new ResponseEntity<>(this.sellerService.getAllSellers(),HttpStatus.OK);
	}
	
	@GetMapping("/{sellerId}")
	public ResponseEntity<Seller> getSellerById(@PathVariable long sellerId) {
		logger.info("getSellerById is called");
		return new ResponseEntity<>(this.sellerService.getSeller(sellerId),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Seller> addSeller(@RequestBody(required=false) Seller seller) {
		logger.info("addSeller is called");
			return new ResponseEntity<>(this.sellerService.addSeller(seller),HttpStatus.OK);
		
		
	}
	
	@GetMapping("/{sellerId}/property/{propertyId}")
	public ResponseEntity<List<Users>> getBuyers(@PathVariable long sellerId,@PathVariable long propertyId){
		logger.info("getBuyers is called");
		return new ResponseEntity<>(this.sellerService.getBuyers(sellerId, propertyId),HttpStatus.OK);
	}
	
	@GetMapping("/{sellerId}/property")
	public ResponseEntity<List<Property>> getListedProperties(@PathVariable long sellerId){
		logger.info("getListedProperties is called");
			return new ResponseEntity<>(this.sellerService.getProperties(sellerId),HttpStatus.OK);		
	}
	
	@DeleteMapping("/{sellerId}/delete")
	public ResponseEntity<HttpStatus> deleteSeller(@PathVariable long sellerId){
		logger.info("deleteSeller is called");
			this.sellerService.deleteSeller(sellerId);
			return new ResponseEntity<>(HttpStatus.OK);
		
	}
	@PostMapping("/login")
	public ResponseEntity<Seller> userLogin(@RequestBody LoginModel model){
		logger.info("userLogin is called");
		Seller seller=this.sellerService.userLogin(model);
		return new ResponseEntity<>(seller,HttpStatus.OK);
	}
}
