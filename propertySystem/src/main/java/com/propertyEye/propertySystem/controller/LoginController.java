package com.propertyEye.propertySystem.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Users;
import com.propertyEye.propertySystem.service.UserService;

@RestController
@RequestMapping("/user")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	
//	to get the list of registered users
	@GetMapping
	public List<Users> getUserList(){
		return userService.getUsers();
	}
	
//	get the single user base on their userId passed
	@GetMapping("/{userId}")
	public Users getUserById(@PathVariable String userId) {
		try{return this.userService.getUser(Long.parseLong(userId));
		}catch(NoSuchElementException e) {
			return null;
		}
		}
	
//	add new user to the user list
	@PostMapping
	public Users addUser(@RequestBody Users user) {
		return this.userService.addUsers(user);
	}
	
//	update the user data of the user whose userId matches with the passed data
	@PutMapping
	public Users updateUser(@RequestBody Users user) {
		return this.userService.updateUsers(user);
	}
	
//	delete the user whose id is given 
	@DeleteMapping("/{userId}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable String userId){
		try {
			this.userService.deleteUser(Long.parseLong(userId));
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/buyer/{buyerId}")
	public List<Property> getPropertyBuyer(@PathVariable long buyerId){
		return this.userService.getPropertyBuyer(buyerId);
	}
	
}
