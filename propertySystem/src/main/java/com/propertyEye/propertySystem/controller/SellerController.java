package com.propertyEye.propertySystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.propertyEye.propertySystem.Exceptions.WrongIdException;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Seller;
import com.propertyEye.propertySystem.entities.Users;
import com.propertyEye.propertySystem.service.SellerService;

@RestController
@RequestMapping("/seller")
public class SellerController {
	
	@Autowired
	private SellerService sellerService;
	
	@GetMapping
	public List<Seller> getAllSeller(){
		return this.sellerService.getAllSellers();
	}
	
	@GetMapping("/{sellerId}")
	public Seller getSellerById(@PathVariable long sellerId) {
		return this.sellerService.getSeller(sellerId);
	}
	
	@PostMapping
	public Seller addSeller(@RequestBody Seller seller) {
		try {
			return this.sellerService.addSeller(seller);
		}
		catch(WrongIdException e) {
			return null;
		}
	}
	
	@GetMapping("/{sellerId}/property/{propertyId}")
	public List<Users> getBuyers(@PathVariable long sellerId,@PathVariable long propertyId){
		return this.sellerService.getBuyers(sellerId, propertyId);
	}
	
	@GetMapping("/{sellerId}/property")
	public List<Property> getListedProperties(@PathVariable long sellerId){
		try {
			return this.sellerService.getProperties(sellerId);
		}catch(WrongIdException e) {
			return null;
		}
	}
	
	@DeleteMapping("/{sellerId}/delete")
	public ResponseEntity<HttpStatus> deleteSeller(@PathVariable long sellerId){
		try {
			this.sellerService.deleteSeller(sellerId);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
