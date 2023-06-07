package com.project.wishlist.api.service;

import java.util.HashSet;
import java.util.Objects;
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
		
		Wishlist wishList = wishListRepository.findByUserId(userId)
				.orElse(new Wishlist(userId, new HashSet<>()));
		
		if (limitOfProductsIntoWishListWasNotReached(wishList.getProductList().size())) {
			
			wishList.getProductList().add(productId);
			
			Wishlist wishListSaved = wishListRepository.save(wishList);
			
			return this.mapToResponse(wishListSaved.getUserId(), wishListSaved.getProductList());
			
		}
		
		return this.mapToResponse(wishList.getUserId(), wishList.getProductList());
		
	}
	
	public WishlistResponse removeProduct(Long userId, Long productId) {
		
		Optional<Wishlist> wishList = wishListRepository.findByUserId(userId);
				
		if (wishList.isPresent()) {
			
			if (wishList.get().getProductList().size() > 1) {
				
				
				wishList.get().getProductList().remove(productId);
				
				
				Wishlist wishListProductRemoved = wishListRepository.save(wishList.get());
				
				
				return this.mapToResponse(wishListProductRemoved.getUserId(), wishListProductRemoved.getProductList());
				
			} else {
				
				
				wishListRepository.delete(wishList.get());
				
			}
			
			
		}		
		
		
		return this.mapToResponse(null,null);
				
	}
	
	public WishlistResponse getAllProductsByUser(Long userId) {
		
		Wishlist wishList = wishListRepository.findByUserId(userId).orElse(new Wishlist());
		
		return this.mapToResponse(wishList.getUserId(), wishList.getProductList());
		
	}
	
	public Boolean isProductInWishListOfUser(Long userId, Long productId) {
		
		Optional<Wishlist> wishList = wishListRepository.findByUserId(userId);
		
		 if (wishList.isEmpty()) {
			 
			 return false;
			 
		 }
		 
		 return wishList.get().getProductList().stream().anyMatch(product -> product.equals(productId));
		

	}
	
	
	private boolean limitOfProductsIntoWishListWasNotReached(int sizeOfList) {
		
		if (sizeOfList < this.wishlistLimitSize) {
			
			return true;
		
		}
		
		return false;
		
	}
	
	
	private WishlistResponse mapToResponse (Long userId, HashSet<Long> productList) {
		
		return new WishlistResponse(userId, productList);
		
	}
	

}
