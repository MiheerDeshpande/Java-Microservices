package com.microservice.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.microservice.user.service.entities.Rating;
import com.microservice.user.service.external.services.RatingService;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private RatingService ratingService;
	
//	@Test
//	void createRating() {
//		Rating rating = Rating.builder()
//				.rating(10)
//				.userId("")
//				.hotelId("")
//				.feedback("this is created using feign client")
//				.build();
//		Rating createRating = ratingService.createRating(rating);
//		
//		System.out.println("new rating " + createRating);
//	}

}
