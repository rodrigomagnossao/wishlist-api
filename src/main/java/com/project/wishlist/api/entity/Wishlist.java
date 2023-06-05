package com.project.wishlist.api.entity;

import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Wishlist {
	
    @Id
	private Long userId;
    private HashSet<Long> productList;
	
	public Wishlist(Long userId, HashSet<Long> productList) {
		this.userId = userId;
		this.productList = productList;
		
	}
	
	public Wishlist() {
		
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public HashSet<Long> getProductList(){
		return this.productList;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public void setProductList(HashSet<Long> productList) {
		this.productList = productList;
		
	}


}
