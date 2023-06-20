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
import com.propertyEye.propertySystem.entities.Users;
import com.propertyEye.propertySystem.service.PropertyService;

@RestController
@RequestMapping("/property")
public class PropertyCotroller {
	
	@Autowired
	private PropertyService propertyService;
	
	@GetMapping
	public List<Property> getAllProperty(){
		return this.propertyService.getAllProeperties();
	}
	
	@GetMapping("/{propertyId}")
	public Property getProperty(@PathVariable long propertyId) {
		return this.propertyService.getProperty(propertyId);
	}
	
	@PostMapping("/{sellerId}/add")
	public Property addNewProperty(@PathVariable long sellerId,@RequestBody Property property) {
		try {
			return this.propertyService.addProperty(sellerId, property);
		}catch(WrongIdException e) {
			return null;
		}
	}
	
	@PostMapping("/{propertyId}/buyer/{buyerId}")
	public Users addNewBuyer(@PathVariable long propertyId,@PathVariable long buyerId) {
		try {
			return this.propertyService.addNewBuyers(propertyId, buyerId);
		}catch(WrongIdException e) {
			return null;
		}
	}
	
	@DeleteMapping("/{propertyId}/delete")
	public ResponseEntity<HttpStatus> deleteProperty(@PathVariable long propertyId){
		try {
			this.propertyService.deleteProperty(propertyId);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
