package com.propertyEye.propertySystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
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
import com.propertyEye.propertySystem.dao.SellerDao;
import com.propertyEye.propertySystem.dao.UserDao;
import com.propertyEye.propertySystem.entities.LoginModel;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Seller;
import com.propertyEye.propertySystem.entities.Users;

public class SellerService_Test {

	@Mock
	private SellerDao sellerDao;
	
	@Mock
	private PropertyDao propertyDao;
	
	@Mock
	private UserDao userDao;
	
	@Mock
	private PropertyServiceImpl propertyService;
	
	@InjectMocks
	private SellerServiceImpl sellerService;
	
	private Seller seller_1=new Seller(1l,"god","god@mail.com","godhead","9999999999","heaven");
	private Seller seller_2=new Seller(2l,"devil","loki@mail.com","lokiland","1111111111","hell");
	private Property property_1= new Property(1l,"rajesh villa","house", "up");
    private Property property_2= new Property(2l,"rastogi house","villa", "lucknow");
	
	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	void testGetAllSellers_Success() {
		when(this.sellerDao.findAll()).thenReturn(Arrays.asList(seller_1,seller_2));
		List<Seller> result=this.sellerService.getAllSellers();
		
		assertNotNull(result);
		assertThat(result).size().isEqualTo(2);
		
		verify(this.sellerDao).findAll();
	}
	
	@Test
	void testGetAllSellers_EmptyList_ThrowsEmptyOutputException(){
		when(this.sellerDao.findAll()).thenReturn(Collections.<Seller>emptyList());
		
		assertThrows(EmptyOutputException.class,()->{
			this.sellerService.getAllSellers();
		});
	}
	
	@Test
	void testGetSeller_Success() {
		when(this.sellerDao.findById(1l)).thenReturn(Optional.of(seller_1));
		Seller result=this.sellerService.getSeller(1l);
		
		assertNotNull(result);
		assertEquals(result.getSellerEmail(),seller_1.getSellerEmail());
		
		verify(this.sellerDao).findById(1l);
	}
	
	@Test
	void testAddSeller_Success() {
		Seller result=this.sellerService.addSeller(seller_1);
		when(this.sellerDao.findBySellerEmail(seller_1.getSellerEmail())).thenReturn(null);
		
		assertNotNull(result);
		assertEquals(result.getSellerEmail(),seller_1.getSellerEmail());
		
		verify(this.sellerDao).findBySellerEmail(seller_1.getSellerEmail());
		verify(this.sellerDao).save(seller_1);
	}
	
	@Test
	void testAddSeller_MissingRequestBody_ThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,()->{
			this.sellerService.addSeller(null);
		});
		
		Seller seller=new Seller();
		assertThrows(IllegalArgumentException.class,()->{
			this.sellerService.addSeller(seller);
		});
		
	}
	@Test
	void testAddSeller_IncompleteRequestBody_ThrowsEmptyInputException() {
		Seller seller=new Seller();
		seller.setSellerName("");
        assertThrows(EmptyInputException.class, () -> {
            sellerService.addSeller(seller);
        });
        seller.setSellerName(null);
        seller.setSellerPassword("");
        assertThrows(EmptyInputException.class, () -> {
            sellerService.addSeller(seller);
        });
        
        seller.setSellerPassword(null);
        seller.setSellerEmail("");
        assertThrows(EmptyInputException.class, () -> {
            sellerService.addSeller(seller);
        });
        
        seller.setSellerEmail(null);
        seller.setSellerPhone("");
        assertThrows(EmptyInputException.class, () -> {
            sellerService.addSeller(seller);
        });
        
        seller.setSellerName("name");
        seller.setSellerPassword(null);
        assertThrows(EmptyInputException.class, () -> {
            sellerService.addSeller(seller);
        });
        
        seller.setSellerPassword("");
        assertThrows(EmptyInputException.class, () -> {
            sellerService.addSeller(seller);
        });
        
        seller.setSellerPassword("password");
        seller.setSellerEmail(null);
        assertThrows(EmptyInputException.class, () -> {
            sellerService.addSeller(seller);
        });
        
        seller.setSellerEmail("");
        assertThrows(EmptyInputException.class, () -> {
            sellerService.addSeller(seller);
        });
        
        seller.setSellerEmail("email@mail.com");
        seller.setSellerPhone(null);
        assertThrows(EmptyInputException.class, () -> {
            sellerService.addSeller(seller);
        });
        
        seller.setSellerPhone("");
        assertThrows(EmptyInputException.class, () -> {
            sellerService.addSeller(seller);
        });
        
	}
	
	@Test
	void testAddSeller_SellerAlreadyExists_ThrowsUniqueValueException() {
		when(this.sellerDao.findBySellerEmail(seller_1.getSellerEmail())).thenReturn(seller_1);
		
		assertThrows(UniqueValueException.class,()->{
			this.sellerService.addSeller(seller_1);
		});
	}
	
	  @Test
	    void testDeleteSeller_Success() {

	        when(sellerDao.existsById(seller_1.getSellerId())).thenReturn(true);
	        when(this.sellerService.getProperties(seller_1.getSellerId())).thenReturn(Arrays.asList(property_1,property_2));

	        sellerService.deleteSeller(seller_1.getSellerId());
	        
	        
	        verify(this.propertyService).deleteProperty(property_1.getPropId());
	        verify(this.propertyService).deleteProperty(property_2.getPropId());
	        verify(sellerDao).deleteById(seller_1.getSellerId());
	    }
	  
	  @Test
	  void testGetBuyers_Success() {
		  property_1.setSeller(seller_1);
		  when(this.sellerDao.existsById(seller_1.getSellerId())).thenReturn(true);
		  when(this.propertyDao.findById(property_1.getPropId())).thenReturn(Optional.of(property_1));
		  when(this.userDao.findUsersByInterestedPropertyPropId(property_1.getPropId())).thenReturn(Arrays.asList(new Users()));
		  
		  List<Users> result=this.sellerService.getBuyers(seller_1.getSellerId(), property_1.getPropId());
		  
		  assertNotNull(result);
		  assertThat(result).size().isGreaterThan(0);
		  
		  verify(this.sellerDao).existsById(seller_1.getSellerId());
		  verify(this.propertyDao).findById(property_1.getPropId());
		  verify(this.userDao).findUsersByInterestedPropertyPropId(property_1.getPropId());
		  property_1.setSeller(null);
	  }
	  
	  @Test
	  void testGetBuyers_NoSellerExists_throwsNoSuchElementException() {
		  when(this.sellerDao.existsById(seller_1.getSellerId())).thenReturn(false);
		  
		  assertThrows(NoSuchElementException.class,()->{
			 this.sellerService.getBuyers(seller_1.getSellerId(), property_1.getPropId());
		  });
		  
	  }
	  
	  @Test
	  void testGetBuyers_WrongSeller_throwsIllegalArgumentException() {
		  property_1.setSeller(seller_2);
		  when(this.sellerDao.existsById(seller_1.getSellerId())).thenReturn(true);
		  when(this.propertyDao.findById(property_1.getPropId())).thenReturn(Optional.of(property_1));
		  
		  assertThrows(IllegalArgumentException.class,()->{
			 this.sellerService.getBuyers(seller_1.getSellerId(), property_1.getPropId()); 
		  });
		  
		  property_1.setSeller(null);
	  }
	  
	  @Test
	  void testGetProperties_NoSellerFound_ThrowsNoSuchElementException() {
		  when(this.sellerDao.existsById(seller_1.getSellerId())).thenReturn(false);
		  assertThrows(NoSuchElementException.class,()->{
			 this.sellerService.getProperties(seller_1.getSellerId()); 
		  });
	  }
	  
	  @Test
	  void testUserLogin_Success() {
		  LoginModel model=new LoginModel();
		  model.setEmailId(seller_1.getSellerEmail());
		  model.setPassword(seller_1.getSellerPassword());
		  when(this.sellerDao.userLogin(model.getEmailId(), model.getPassword())).thenReturn(seller_1);
		  
		  Seller result=this.sellerService.userLogin(model);
		  
		  assertNotNull(result);
		  assertEquals(result.getSellerEmail(),seller_1.getSellerEmail());
		  assertEquals(result.getSellerPassword(),seller_1.getSellerPassword());
		  
		  verify(this.sellerDao).userLogin(seller_1.getSellerEmail(), seller_1.getSellerPassword());
	  }
	  
	  @Test
	  void testUserLogin_NoSellerFound_ThrowsEmptyOutputException() {
		  LoginModel model=new LoginModel();
		  model.setEmailId(seller_1.getSellerEmail());
		  model.setPassword(seller_1.getSellerPassword());
		  when(this.sellerDao.userLogin(model.getEmailId(), model.getPassword())).thenReturn(null);
		  assertThrows(EmptyOutputException.class,()->{
			  this.sellerService.userLogin(model);
		  });
	  }
}
