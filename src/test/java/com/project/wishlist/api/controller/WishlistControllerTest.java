package com.project.wishlist.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.project.wishlist.api.response.WishlistResponse;
import com.project.wishlist.api.service.WishlistService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WishlistControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private WishlistService wishListService;
	
    @DisplayName("should add a product into specific user`s wishlist")
    @Test
    public void shouldAddProductIntoUsersWishlist() throws Exception {
    	
    	Long userId    = 22L;
    	Long productId = 300L;
    	    	
    	ResponseEntity<WishlistResponse> response = 
    			testRestTemplate.exchange("/wishlist/{userId}/product/{productId}",
    		    HttpMethod.POST,
    		    null,
    		    WishlistResponse.class,
    		    userId, productId);
    	
    	assertEquals(userId, response.getBody().getUserId());
    	
    	assertTrue(response.getBody().getProductList().stream()
    			.allMatch(productIdOfLIst -> productId.equals(productId)));

    	
    }
    
    @DisplayName("should delete a product of specific user`s wishlist")
    @Test
    public void shouldRemoveProductOfUsersWishlist() throws Exception {
    	
    	Long userId    = 22L;
    	Long productId1 = 300L;
    	Long productId2 = 500L;
    	
    	wishListService.addProduct(userId, productId1);
    	wishListService.addProduct(userId, productId2);
    	    	
    	ResponseEntity<WishlistResponse> response = 
    			testRestTemplate.exchange("/wishlist/{userId}/product/{productId}",
    		    HttpMethod.DELETE,
    		    null,
    		    WishlistResponse.class,
    		    userId, productId1);
    	
    	assertEquals(userId, response.getBody().getUserId());
    	
    	assertFalse(response.getBody().getProductList().contains(productId1));

    	
    }
    
    
    @DisplayName("should get a product into wishlist of specific user")
    @Test
    public void shouldGetProductByUser() throws Exception {
    	
    	Long userId    = 22L;
    	Long productId = 100L;
    
    	wishListService.addProduct(userId, productId);
    	
    	ResponseEntity<WishlistResponse> response = testRestTemplate.exchange("/wishlist/{userId}",
    		    HttpMethod.GET,
    		    null,
    		    WishlistResponse.class,
    		    userId);
    	
    	assertTrue(response.getBody().getProductList().stream()
    			.allMatch(productIdOfLIst -> productId.equals(productId)));

    	
    }
    
    @DisplayName("should return true if product exists into wishlist of specific user")
    @Test
    public void shouldReturnTrueIfProductExistsIntoUsersWishlist() throws Exception {
    	
    	
    	Long userId     = 22L;
    	Long productId1 = 100L;
    	Long productId2 = 900L;
    	
    	wishListService.addProduct(userId, productId1);
    	wishListService.addProduct(userId, productId2);
    	
    	ResponseEntity<Boolean> response = testRestTemplate.exchange("/wishlist/{userId}/product/{productId}/exist",
    		    HttpMethod.GET,
    		    null,
    		    Boolean.class,
    		    userId, productId2);
    	
    	assertTrue(response.getBody());

    	
    }
    
    @DisplayName("should return false if product not exists into wishlist of specific user")
    @Test
    public void shouldReturnFalseIfProductNotExistsIntoUsersWishlist() throws Exception {
    	
    	
    	Long userId     = 22L;
    	Long productId1 = 100L;
    	Long productId2 = 900L;
    	Long productNotExists = 143L;
    	
    	wishListService.addProduct(userId, productId1);
    	wishListService.addProduct(userId, productId2);
    	
    	ResponseEntity<Boolean> response = testRestTemplate.exchange("/wishlist/{userId}/product/{productId}/exist",
    		    HttpMethod.GET,
    		    null,
    		    Boolean.class,
    		    userId, productNotExists);
    	
    	assertFalse(response.getBody());

    	
    }
    
}
