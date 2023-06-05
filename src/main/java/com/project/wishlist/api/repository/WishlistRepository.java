package com.project.wishlist.api.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.wishlist.api.entity.Wishlist;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, Long>{
	
	public Optional<Wishlist> findByUserId(Long userId);
	
}
