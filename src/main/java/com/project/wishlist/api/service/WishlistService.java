package com.project.wishlist.api.service;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.wishlist.api.entity.Wishlist;
import com.project.wishlist.api.repository.WishlistRepository;
import com.project.wishlist.api.response.WishlistResponse;

@Service
public class WishlistService {
	
	@Value("${wishlist.limit.size}")
	int wishlistLimitSize;

	final WishlistRepository wishListRepository;
	
	public WishlistService(WishlistRepository wishListRepository) {
		this.wishListRepository = wishListRepository;
		
	}
	
	
	public WishlistResponse addProduct(Long userId, Long productId) {
		
		Wishlist wishList = this.findUser(userId).get();
		
		if (limitOfProductsIntoWishListWasNotReached(wishList.getProductList().size())) {
			
			wishList.getProductList().add(productId);
			return this.mapToResponse(wishListRepository.save(wishList));
			
		}
		
		return this.mapToResponse(wishList);
		
	}
	
	public WishlistResponse removeProduct(Long userId, Long productId) {
		
		Wishlist wishList = this.findUser(userId)
				.orElse(new Wishlist(userId, new HashSet<>()));
		wishList.getProductList().remove(productId);
		
		return this.mapToResponse(wishListRepository.save(wishList));
				
		
	}
	
	public WishlistResponse getAllProductsByUser(Long userId) {
		
		Optional<Wishlist> wishList = this.findUser(userId);
		
		 return this.mapToResponse(wishList.get());

		
	}
	
	public Boolean isProductInWishListOfUser(Long userId, Long productId) {
		
		Optional<Wishlist> wishList = this.findUser(userId);
		
		 if (wishList.isEmpty()) {
			 
			 return false;
			 
		 }
		 
		 return wishList.get().getProductList().stream().anyMatch(product -> product.equals(productId));
		

	}
	
	private Optional<Wishlist> findUser(Long userId) {
		
		Optional<Wishlist> wishList = wishListRepository.findByUserId(userId);
		
		if (wishList.isEmpty()) {
			
			return Optional.of(new Wishlist(userId, new HashSet<>()));
		
		} else {
			
			return wishList;
		
		}
		
	}
	
	private boolean limitOfProductsIntoWishListWasNotReached(int sizeOfList) {
		
		if (sizeOfList < this.wishlistLimitSize) {
			
			return true;
		
		}
		
		return false;
		
	}
	
	private WishlistResponse mapToResponse (Wishlist wishList) {
		
		return new WishlistResponse(wishList.getUserId(), wishList.getProductList());
		
	}
	

}
