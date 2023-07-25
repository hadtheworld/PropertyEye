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

import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Users;
import com.propertyEye.propertySystem.logger.PropertyLogger;
import com.propertyEye.propertySystem.service.PropertyService;

@RestController
@CrossOrigin
@RequestMapping("/property")
public class PropertyCotroller {
	
	Logger logger=PropertyLogger.getLogger(LoginController.class);
	
	@Autowired
	private PropertyService propertyService;
	
	@GetMapping
	public ResponseEntity<List<Property>> getAllProperty(){
		logger.info("getAllProperty is called");
		return new ResponseEntity<>(this.propertyService.getAllProeperties(),HttpStatus.OK);
	}
	
	@GetMapping("/{propertyId}")
	public ResponseEntity<Property> getProperty(@PathVariable long propertyId) {
		logger.info("getProperty is called");
		return new ResponseEntity<>(this.propertyService.getProperty(propertyId),HttpStatus.OK);
	}
	
	@PostMapping("/{sellerId}/add")
	public ResponseEntity<Property> addNewProperty(@PathVariable long sellerId,@RequestBody(required=false) Property property) {
		logger.info("addNewProperty is called");
			return new ResponseEntity<>(this.propertyService.addProperty(sellerId, property),HttpStatus.OK);
	}
	
	@PostMapping("/{propertyId}/buyer/{buyerId}")
	public ResponseEntity<Users> addNewBuyer(@PathVariable long propertyId,@PathVariable long buyerId) {
		logger.info("addNewBuyer is called");
			return new ResponseEntity<>(this.propertyService.addNewBuyers(propertyId, buyerId),HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{propertyId}/delete")
	public ResponseEntity<HttpStatus> deleteProperty(@PathVariable long propertyId){
		logger.info("deleteProperty is called");
			this.propertyService.deleteProperty(propertyId);
			return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
}
