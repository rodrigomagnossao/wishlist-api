package com.project.wishlist.api.response;

import java.util.HashSet;

public class WishlistResponse {
	
	private final Long userId;
	private final HashSet<Long> productList;
	
	public WishlistResponse(Long userId, HashSet<Long> productList){
		this.userId = userId;
		this.productList = productList;
		
	}
	
	
	public Long getUserId() {
		return this.userId;
	}
	
	public HashSet<Long> getProductList(){
		return this.productList;
	}
		
	
}
