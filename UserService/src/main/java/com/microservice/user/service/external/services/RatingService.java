package com.microservice.user.service.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservice.user.service.entities.Rating;

@Service
@FeignClient(name = "RATINGSERVICE")
public interface RatingService {

	//get
	@GetMapping("/ratings/users/{userId}")
	Rating[] getRatingsByUserId(@PathVariable("userId") String userId);
	
	//post
	@PostMapping("/ratings")
	Rating createRating(@RequestBody Rating values);
	
	//put
	@GetMapping("/ratings/{ratingId}")
	Rating updateRating(@PathVariable("ratingId") String ratingId,@RequestBody Rating rating);
	
	//delete
	@DeleteMapping("/ratings/{ratingId}")
	void deleteRating(@PathVariable("ratingId") String ratingId);
}
