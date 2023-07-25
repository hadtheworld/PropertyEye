package com.propertyEye.propertySystem.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.propertyEye.propertySystem.Exceptions.EmptyInputException;
import com.propertyEye.propertySystem.Exceptions.EmptyOutputException;
import com.propertyEye.propertySystem.Exceptions.UniqueValueException;
import com.propertyEye.propertySystem.dao.PropertyDao;
import com.propertyEye.propertySystem.dao.UserDao;
import com.propertyEye.propertySystem.entities.LoginModel;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Users;

public class UserService_Test {

	@Mock
	private PropertyDao propertyDao;
	
	@Mock
	private UserDao userDao;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	private Users user_1=new Users(1l,"god","god@mail.com","godhead","9999999999","heaven");
	@Test
	void testGetUsers_Success() {
		when(this.userDao.findAll()).thenReturn(Arrays.asList(new Users(),new Users()));
		
		List<Users> result= this.userService.getUsers();
		
		assertNotNull(result);
		assertThat(result).size().isEqualTo(2);
		
		verify(this.userDao).findAll();
	}
	
	@Test
	void testGetUsers_NoUserFound_ThrowsEmptyOutputException() {
		when(this.userDao.findAll()).thenReturn(Collections.<Users>emptyList());
		
		assertThrows(EmptyOutputException.class,()->{
			this.userService.getUsers();
		});
	}
	
	@Test
	void testGetUser_Success() {
		when(this.userDao.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
		
		Users result=this.userService.getUser(user_1.getUserId());
		
		assertNotNull(result);
		assertEquals(result.getUserEmail(),user_1.getUserEmail());
		
		verify(this.userDao).findById(user_1.getUserId());
	}
	
	@Test
	void testAddUsers_Success() {
		when(this.userDao.findByUserEmail(user_1.getUserEmail())).thenReturn(null);
		
		Users result=this.userService.addUsers(user_1);
		
		assertNotNull(result);
		assertEquals(result.getUserEmail(),user_1.getUserEmail());
		assertEquals(result.getUserName(),user_1.getUserName());
		assertEquals(result.getUserPassword(),user_1.getUserPassword());
		
		verify(this.userDao).findByUserEmail(user_1.getUserEmail());
		verify(this.userDao).save(user_1);
	}
	
	@Test
	void testAddUser_MissingRequestBody_ThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,()->{
			this.userService.addUsers(null);
		});
		
		Users user=new Users();
		assertThrows(IllegalArgumentException.class,()->{
			this.userService.addUsers(user);
		});
		
	}
	@Test
	void testAddSeller_IncompleteRequestBody_ThrowsEmptyInputException() {
		Users user=new Users();
		user.setUserName("");
        assertThrows(EmptyInputException.class, () -> {
            userService.addUsers(user);
        });
        user.setUserName(null);
        user.setUserPassword("");
        assertThrows(EmptyInputException.class, () -> {
            userService.addUsers(user);
        });
        
        user.setUserPassword(null);
        user.setUserEmail("");
        assertThrows(EmptyInputException.class, () -> {
            userService.addUsers(user);
        });
        
        user.setUserEmail(null);
        user.setUserPhone("");
        assertThrows(EmptyInputException.class, () -> {
            userService.addUsers(user);
        });
        
        user.setUserName("name");
        user.setUserPassword(null);
        assertThrows(EmptyInputException.class, () -> {
            userService.addUsers(user);
        });
        
        user.setUserPassword("");
        assertThrows(EmptyInputException.class, () -> {
            userService.addUsers(user);
        });
        
        user.setUserPassword("password");
        user.setUserEmail(null);
        assertThrows(EmptyInputException.class, () -> {
            userService.addUsers(user);
        });
        
        user.setUserEmail("");
        assertThrows(EmptyInputException.class, () -> {
            userService.addUsers(user);
        });
        
        user.setUserEmail("email@mail.com");
        user.setUserPhone(null);
        assertThrows(EmptyInputException.class, () -> {
            userService.addUsers(user);
        });
        
        user.setUserPhone("");
        assertThrows(EmptyInputException.class, () -> {
            userService.addUsers(user);
        });
        
	}
	
	@Test
	void testAddSeller_SellerAlreadyExists_ThrowsUniqueValueException() {
		when(this.userDao.findByUserEmail(user_1.getUserEmail())).thenReturn(user_1);
		
		assertThrows(UniqueValueException.class,()->{
			this.userService.addUsers(user_1);
		});
	}
	
	@Test
	void testDeleteUser_Success() {
		Property property=new Property();
		property.setBuyers(new LinkedHashSet<>(Arrays.asList(user_1)));
		
		when(this.userDao.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
		when(this.userDao.existsById(user_1.getUserId())).thenReturn(true);
		when(this.userService.getPropertyBuyer(user_1.getUserId())).thenReturn(Arrays.asList(property));
		
		this.userService.deleteUser(user_1.getUserId());
		
		verify(this.userDao).findById(user_1.getUserId());
		verify(this.propertyDao).save(property);
		verify(this.userDao).save(user_1);
		verify(this.userDao).deleteById(user_1.getUserId());
	}
	
	@Test
	void testGetPropertyBuyer_NoBuyerFound_ThrowsNoSuchElementException() {
		when(this.userDao.existsById(user_1.getUserId())).thenReturn(false);
		assertThrows(NoSuchElementException.class,()->{
			this.userService.getPropertyBuyer(user_1.getUserId());
		});
	}
	
	@Test
	void testUserLogin_Success() {
		LoginModel model=new LoginModel();
		model.setEmailId(user_1.getUserEmail());
		model.setPassword(user_1.getUserPassword());
		when(this.userDao.userLogin(model.getEmailId(),model.getPassword())).thenReturn(user_1);
		
		Users result=this.userService.userLogin(model);
		
		assertNotNull(result);
		assertEquals(result.getUserEmail(),user_1.getUserEmail());
		assertEquals(result.getUserPassword(),user_1.getUserPassword());
		
		verify(this.userDao).userLogin(model.getEmailId(), model.getPassword());
		
	}
	
	@Test
	void testUserLogin_NoUserFound_ThrowsEmptyOutputException() {
		LoginModel model=new LoginModel();
		model.setEmailId(user_1.getUserEmail());
		model.setPassword(user_1.getUserPassword());
		when(this.userDao.userLogin(model.getEmailId(), model.getPassword())).thenReturn(null);
		
		assertThrows(EmptyOutputException.class,()->{
			this.userService.userLogin(model);
		});
	}
}
