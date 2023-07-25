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
import com.propertyEye.propertySystem.entities.Users;
import com.propertyEye.propertySystem.logger.PropertyLogger;
import com.propertyEye.propertySystem.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class LoginController {
	Logger logger=PropertyLogger.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	
	
//	to get the list of registered users
	@GetMapping
	public ResponseEntity<List<Users>> getUserList(){
		logger.info("getUserList is called");
		return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
	}
	
//	get the single user base on their userId passed
	@GetMapping("/{userId}")
	public ResponseEntity<Users> getUserById(@PathVariable String userId) {
		logger.info("getUserById is called");
		return new ResponseEntity<>(this.userService.getUser(Long.parseLong(userId)),HttpStatus.OK);
	}
	
//	add new user to the user list
	@PostMapping
	public ResponseEntity<Users> addUser(@RequestBody(required=false) Users user) {
		logger.info("addUser is called");
		return new ResponseEntity<>(this.userService.addUsers(user),HttpStatus.OK);
	}


	
//	update the user data of the user whose userId matches with the passed data
	
	
//	delete the user whose id is given 
	@DeleteMapping("/{userId}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable String userId){
		logger.info("deleteUser is called");
			this.userService.deleteUser(Long.parseLong(userId));
			return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	
	@GetMapping("/buyer/{buyerId}")
	public ResponseEntity<List<Property>> getPropertyBuyer(@PathVariable long buyerId){
		logger.info("getPropertyBuyer is called");
		return new ResponseEntity<>(this.userService.getPropertyBuyer(buyerId),HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Users> userLogin(@RequestBody LoginModel model){
		logger.info("userLogin is called");
		Users user=this.userService.userLogin(model);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
}
