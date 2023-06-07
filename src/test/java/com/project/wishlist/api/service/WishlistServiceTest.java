package com.project.wishlist.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.wishlist.api.response.WishlistResponse;

@AutoConfigureDataMongo
@SpringBootTest
public class WishlistServiceTest {
	
	@Value("${wishlist.limit.size}")
	int wishlistLimitSize;
	
	@Autowired
	WishlistService wishListService;
	
    @DisplayName("should add a product in a wishlist")
    @Test
    public void shouldAddProduct() {
    	
    	Long userId = 300L;
    	Long productId = 90L;
    	
    	wishListService.addProduct(userId, productId);
    	
    	WishlistResponse wishListFound = wishListService.getAllProductsByUser(userId);
    	
    	assertFalse(wishListFound.getProductList().isEmpty());
    	assertThat(wishListFound.getProductList().contains(productId));
    
    }
    
    @DisplayName("should remove a product in a wishlist")
    @Test
    public void shouldRemoveProduct() {
    	
    	Long userId     = 700L;
    	Long productId1 = 40L;
    	Long productId2 = 150L;
    	
    	wishListService.addProduct(userId, productId1);
    	wishListService.addProduct(userId, productId2);
    	
    	WishlistResponse wishListFound = wishListService.getAllProductsByUser(userId);
    	
    	assertFalse(wishListFound.getProductList().isEmpty());
    	assertThat(wishListFound.getProductList().contains(productId2));
    	
    	wishListService.removeProduct(userId, productId2);
    	
    	WishlistResponse wishListFoundAfterRemoveProduct = wishListService.getAllProductsByUser(userId);
    	
    	assertFalse(wishListFoundAfterRemoveProduct.getProductList().contains(productId2));
    	
    	
    }
    
    @DisplayName("should delete a user in a wishlist when all products are removed")
    @Test
    public void shouldDeleteUser() {
    	
    	Long userId     = 700L;
    	Long productId1 = 40L;
    	Long productId2 = 150L;
    	
    	wishListService.addProduct(userId, productId1);
    	wishListService.addProduct(userId, productId2);
    	
    	WishlistResponse wishListFound = wishListService.getAllProductsByUser(userId);
    	
    	assertFalse(wishListFound.getProductList().isEmpty());
    	assertThat(wishListFound.getProductList().contains(productId2));
    	
    	wishListService.removeProduct(userId, productId2);
    	
    	WishlistResponse wishListFoundAfterRemoveProduct = wishListService.getAllProductsByUser(userId);
    	
    	assertFalse(wishListFoundAfterRemoveProduct.getProductList().contains(productId2));
    	assertTrue(wishListFoundAfterRemoveProduct.getProductList().contains(productId1));
    	
    	wishListService.removeProduct(userId, productId1);
    	
    	WishlistResponse wishListWithLastProductRemoved = wishListService.getAllProductsByUser(userId);
    	
    	assertNull(wishListWithLastProductRemoved.getUserId());
    	assertNull(wishListWithLastProductRemoved.getProductList());
    	
    }
    
    @DisplayName("should return null when userId not found when remove a product")
    @Test
    public void shouldReturnNullWhenProductIsRemoved() {
    	
    	Long userId     = 700L;
    	Long productId1 = 40L;
    	Long productId2 = 150L;
    	
    	WishlistResponse wishListNotFound = wishListService.getAllProductsByUser(userId);
    	
    	wishListService.removeProduct(userId, productId2);
    	
    	assertNull(wishListNotFound.getUserId());
    	assertNull(wishListNotFound.getProductList());
    	
    }
    
    
    
    @DisplayName("should find all products of user`s wishlist")
    @Test
    public void shouldFindProductsByUser() {
    	
    	Long userId     = 97L;
    	Long productId1 = 11L;
    	Long productId2 = 12L;
    	
    	wishListService.addProduct(97L, 11L);
    	wishListService.addProduct(97L, 12L);
    	
    	WishlistResponse wishListFound = wishListService.getAllProductsByUser(userId);
    	
    	assertFalse(wishListFound.getProductList().isEmpty());
    	assertTrue(wishListFound.getProductList().containsAll(Set.of(productId1, productId2)));
    	assertEquals(wishListFound.getProductList().size(), 2);
    	 
    	
    }
    
    @DisplayName("should returns true if product exists for the user")
    @Test
    public void shouldReturnsTrueIfProductExistsForUser () {
    	
    	Long userId     = 33L;
    	Long productId1 = 113L;
    	Long productId2 = 114L;
    	
    	wishListService.addProduct(userId, productId1);
    	wishListService.addProduct(userId, productId2);
    	
    	boolean isProductExists = wishListService.isProductInWishListOfUser(userId, productId1);
    	
    	assertTrue(isProductExists);
    	
    }
    
    @DisplayName("should returns false if product not exists for the user")
    @Test
    public void shouldReturnsFalseIfProductNotExistsForUser () {
    	
    	Long userId          = 33L;
    	Long productId1      = 113L;
    	Long productId2      = 114L;
    	Long productNotAdded = 117L;
    	
    	wishListService.addProduct(userId, productId1);
    	wishListService.addProduct(userId, productId2);
    	
    	boolean isProductExists = wishListService.isProductInWishListOfUser(userId, productNotAdded);
    	
    	assertFalse(isProductExists);
    	
    }
    
    @DisplayName("should returns null if user not exist")
    @Test
    public void shouldReturnsEmptyListIfUserNotExist () {
    	
    	Long userId          = 33L;
    	Long userNotExists   = 87L;
    	Long productId1      = 113L;
    	Long productId2      = 114L;
    	
    	wishListService.addProduct(userId, productId1);
    	wishListService.addProduct(userId, productId2);
    	
    	WishlistResponse wishList = wishListService.getAllProductsByUser(userNotExists);
    	
    	assertNull(wishList.getProductList());
    	
    }
    
    @DisplayName("should add until 20 products into wishlist")
    @Test
    public void shouldAddUntilTwentyProductsIntoWishList() {
    	
    	Long userId = 70L;
    	    	
    	for (Long productId = 1L; productId <= wishlistLimitSize+1; productId++) {
    		wishListService.addProduct(userId, productId);
    	}

    	WishlistResponse response = wishListService.getAllProductsByUser(userId);
    	assertEquals(response.getProductList().size(), 20);	
    	
    }
    
    
}
