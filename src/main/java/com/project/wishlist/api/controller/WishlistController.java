package com.project.wishlist.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.wishlist.api.response.WishlistResponse;
import com.project.wishlist.api.service.WishlistService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

	
	private final WishlistService wishListService;

	WishlistController(WishlistService wishListService) {
		this.wishListService = wishListService;
	}

	@PostMapping("/{userId}/product/{productId}")
	@Tag(name = "Add Product", description = "Endpoint to add a product into the wishlist")
	public ResponseEntity<WishlistResponse> addProduct(@PathVariable Long userId, @PathVariable Long productId) {

		return ResponseEntity.status(HttpStatus.CREATED).body(wishListService.addProduct(userId, productId));

	}

    @DeleteMapping("/{userId}/product/{productId}")
	@Tag(name = "Delete Product", description = "Endpoint to remove a product of the wishlist")
	public ResponseEntity<WishlistResponse> removeProduct(@PathVariable Long userId, @PathVariable Long productId) {

		return ResponseEntity.ok(wishListService.removeProduct(userId, productId));

	}

	@GetMapping("/{userId}")
	@Tag(name = "Find Product", description = "Endpoint to get the products of user`s wishlist")
	public ResponseEntity<?> getProductsByClient(@PathVariable Long userId) {
		
		return ResponseEntity.ok(wishListService.getAllProductsByUser(userId));

	}

	@GetMapping("/{userId}/product/{productId}/exist")
	@Tag(name = "Exist Product", description = "Endpoint to verify if user wishlist`s product exists")

	public ResponseEntity<Boolean> isProductInWishListOfUser(@PathVariable Long userId, @PathVariable Long productId) {

		return ResponseEntity.ok(wishListService.isProductInWishListOfUser(userId, productId));

	}

}
