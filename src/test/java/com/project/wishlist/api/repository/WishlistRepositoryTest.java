package com.project.wishlist.api.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.project.wishlist.api.entity.Wishlist;

@DataMongoTest
public class WishlistRepositoryTest {
	
	@Autowired
	WishlistRepository wishListRepository;
	
    @DisplayName("should add and find a product in the user`s whishlist")
    @Test
    public void shouldAddAndFindProductAndFindProductByUserId() {
    	
    	Long userId = 100L;
    	
    	HashSet<Long> productList = new HashSet<>();
    	productList.add(20L);
    	Wishlist wishList = new Wishlist(userId, productList);
    	wishListRepository.save(wishList);
    	
    	Optional<Wishlist> wishListFound = wishListRepository.findByUserId(userId);
    	
    	assertFalse(wishListFound.get().getProductList().isEmpty());
    	assertThat(wishListFound.get().getProductList().contains(20L));
    	
    }

}
