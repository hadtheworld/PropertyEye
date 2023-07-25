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
import com.propertyEye.propertySystem.dao.PropertyDao;
import com.propertyEye.propertySystem.dao.SellerDao;
import com.propertyEye.propertySystem.dao.UserDao;
import com.propertyEye.propertySystem.entities.Property;
import com.propertyEye.propertySystem.entities.Seller;
import com.propertyEye.propertySystem.entities.Users;

//@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
class PropertyService_Test {

    @Mock
    private PropertyDao propertyDao;

    @Mock
    private UserDao userDao;

    @Mock
    private SellerDao sellerDao;

    @InjectMocks
    private PropertyServiceImpl propertyService;
    
    private Property property= new Property(1l,"rajesh villa","house", "up");
    private Property property_2= new Property(2l,"rastogi house","villa", "lucknow");
    
    private Users buyer= new Users(1l,"god","god@mail.com","godhead","9999999999","heaven");
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProperty_Success() {
        long sellerId = 1L;
        Property property = new Property();
        property.setPropName("Property Name");
        property.setPropLocation("Property Location");
        property.setPropDescription("Property Description");

        when(sellerDao.findById(sellerId)).thenReturn(Optional.of(new Seller()));
        when(propertyDao.save(property)).thenReturn(property);

        Property result = propertyService.addProperty(sellerId, property);

        assertNotNull(result);
        assertEquals(property.getPropName(), result.getPropName());
        assertEquals(property.getPropLocation(), result.getPropLocation());
        assertEquals(property.getPropDescription(), result.getPropDescription());

        verify(sellerDao).findById(sellerId);
        verify(propertyDao).save(property);
    }

    @Test
    void testAddProperty_NullRequestBody_ThrowsIllegalArgumentException() throws Exception{
        long sellerId = 1L;

        assertThrows(IllegalArgumentException.class, () -> {
            propertyService.addProperty(sellerId, null);
        });
    }

    @Test
    void testAddProperty_MissingPropertyFields_ThrowsIllegalArgumentException() {
        long sellerId = 1L;
        Property property = new Property();
        

        assertThrows(IllegalArgumentException.class, () -> {
            propertyService.addProperty(sellerId, property);
        });
        
    }
    
    @Test
    void testAddProperty_MissingPropertyFields_ThrowsEmptyInputException() {
        long sellerId = 1L;
        Property property = new Property();
        property.setPropDescription("");
        
        
        assertThrows(EmptyInputException.class, () -> {
            propertyService.addProperty(sellerId, property);
        });
        property.setPropLocation("");
        property.setPropName("dsfd");
        assertThrows(EmptyInputException.class, () -> {
            propertyService.addProperty(sellerId, property);
        });
        property.setPropLocation("dfsd");
        property.setPropName(null);
        assertThrows(EmptyInputException.class, () -> {
            propertyService.addProperty(sellerId, property);
        });
        property.setPropName("");
        assertThrows(EmptyInputException.class, () -> {
            propertyService.addProperty(sellerId, property);
        });
        property.setPropName("dsaf");
        property.setPropLocation(null);
        assertThrows(EmptyInputException.class, () -> {
            propertyService.addProperty(sellerId, property);
        });
    }
    
    @Test
    void testAddNewBuyers_Success() {
    	when(this.propertyDao.findById(1l)).thenReturn(Optional.of(property));
    	when(this.userDao.findById(1l)).thenReturn(Optional.of(buyer));
    	Users result=this.propertyService.addNewBuyers(1l, 1l);
    	assertNotNull(result);
    	assertEquals(result.getUserEmail(),buyer.getUserEmail());
    	
    	verify(this.propertyDao).findById(1l);
    	verify(this.userDao).findById(1l);
    	verify(this.userDao).save(buyer);
    	verify(this.propertyDao).save(property);
    }
    
    @Test
    void testAddNewBuyers_NoUserFound_ThrowsNoSuchElementException (){
    	when(this.propertyDao.findById(1l)).thenReturn(Optional.of(property));
    	when(this.userDao.findById(1l)).thenReturn(Optional.of(buyer));
    	
    	assertThrows(NoSuchElementException.class,()->{
    		this.propertyService.addNewBuyers(0l, 0l);
    	});
    	assertThrows(NoSuchElementException.class,()->{
    		this.propertyService.addNewBuyers(1l, 0l);
    	});
    	assertThrows(NoSuchElementException.class,()->{
    		this.propertyService.addNewBuyers(0l, 1l);
    	});
    }
    
    @Test
    void testGetProperty_Success() {
    	when(this.propertyDao.findById(1l)).thenReturn(Optional.of(property));
    	Property result= this.propertyService.getProperty(1l);
    	
    	assertNotNull(result);
    	assertEquals(result.getPropName(),property.getPropName());
    	assertEquals(result.getPropLocation(),property.getPropLocation());
    	assertEquals(result.getPropDescription(),property.getPropDescription());
    	
    	verify(this.propertyDao).findById(1l);
    }
    
    @Test
    void testGetAllProeperties_Success() {
    	when(this.propertyDao.findAll()).thenReturn(Arrays.asList(property,property_2));
    	List<Property> result=this.propertyService.getAllProeperties();
    	
    	assertNotNull(result);
    	assertThat(result).size().isEqualTo(2);
    	
    	verify(this.propertyDao).findAll();
    }
    
    @Test
    void testGetAllProperties_EmptyList_ThrowsEmptyOutputException() {
    	when(this.propertyDao.findAll()).thenReturn(Collections.<Property>emptyList());
    	
    	assertThrows(EmptyOutputException.class,()->{
    		this.propertyService.getAllProeperties();
    	});
    	
    }
    
    @Test
    void testDeleteProperty_Success() {
    	when(this.propertyDao.findById(1l)).thenReturn(Optional.of(property));
    	when(this.userDao.findUsersByInterestedPropertyPropId(1l)).thenReturn(Arrays.asList(buyer));
    	
    	this.propertyService.deleteProperty(1l);
    	
    	verify(this.propertyDao).findById(property.getPropId());
    	verify(this.userDao).save(buyer);
    	verify(this.propertyDao).save(property);
    	verify(this.propertyDao).delete(property);
    }
}
