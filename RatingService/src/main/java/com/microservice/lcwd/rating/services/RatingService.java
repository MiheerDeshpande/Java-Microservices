package com.microservice.lcwd.rating.services;

import java.util.List;

import com.microservice.lcwd.rating.entities.Rating;

public interface RatingService {

	//create
	Rating createRating(Rating rating);
	//get all ratings
	List<Rating> getRatings();
	//get all by userId
	List<Rating> getRatingsByUserId(String userId);
	//get all by hotelId
	List<Rating> getRatingByHotelId(String hotelId);
}
