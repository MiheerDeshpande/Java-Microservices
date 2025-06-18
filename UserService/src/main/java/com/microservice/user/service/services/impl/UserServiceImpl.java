package com.microservice.user.service.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.hibernate.ResourceClosedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.user.service.entities.Hotel;
import com.microservice.user.service.entities.Rating;
import com.microservice.user.service.entities.User;
import com.microservice.user.service.external.services.HotelService;
import com.microservice.user.service.external.services.RatingService;
import com.microservice.user.service.repositories.UserRepository;
import com.microservice.user.service.services.UserService;
import com.netflix.discovery.converters.Auto;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	@Autowired
	private RatingService ratingServie;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return this.userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return this.userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		// get the user from the database with the help of user repository 
		User user =  this.userRepository.findById(userId).orElseThrow(()-> new ResourceClosedException("User with given id is not found on the server"+ userId));
		// fetch rating from the Rating service
		//http://localhost:8083/ratings/users/f1a05c8e-d2b7-4c05-a0c4-1fa9bc2c9713
//		Rating[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/" + user.getUserId(), Rating[].class);
		Rating[] ratingsOfUser = ratingServie.getRatingsByUserId(userId);
		logger.info("{}", ratingsOfUser);
		List<Rating> ratings= Arrays.stream(ratingsOfUser).toList();
		// fetch the rating from the hotel service
		
		
		List<Rating> ratingList = ratings.stream().map( rating -> {
			//api call to hotel service to get hotel
			
			// http://localhost:8082/hotels/cd020f59-29d5-4059-a6e9-3bf4cf421fd6
//			ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//			Hotel hotel = forEntity.getBody();
			Hotel hotel = hotelService.getHotel(rating.getHotelId());
//			logger.info("resp status code ",forEntity.getStatusCode());
			//set the hotel to rating '
			rating.setHotel(hotel);
			
			// return the rating
			return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratingList);
		
		return user;
	}
	
	public User getUserOld(String userId) {
		// get the user from the database with the help of user repository 
		User user =  this.userRepository.findById(userId).orElseThrow(()-> new ResourceClosedException("User with given id is not found on the server"+ userId));
		// fetch rating from the Rating service
		//http://localhost:8083/ratings/users/f1a05c8e-d2b7-4c05-a0c4-1fa9bc2c9713
		Rating[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/" + user.getUserId(), Rating[].class);
		logger.info("{}", ratingsOfUser);
		List<Rating> ratings= Arrays.stream(ratingsOfUser).toList();
		// fetch the rating from the hotel service
		
		
		List<Rating> ratingList = ratings.stream().map( rating -> {
			//api call to hotel service to get hotel
			
			// http://localhost:8082/hotels/cd020f59-29d5-4059-a6e9-3bf4cf421fd6
			ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
			Hotel hotel = forEntity.getBody();
			logger.info("resp status code ",forEntity.getStatusCode());
			//set the hotel to rating '
			rating.setHotel(hotel);
			
			// return the rating
			return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratingList);
		
		return user;
	}


}
