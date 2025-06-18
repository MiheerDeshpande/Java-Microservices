package com.microservice.user.service.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.service.entities.User;
import com.microservice.user.service.entities.User.UserBuilder;
import com.microservice.user.service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
 	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	// create
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User userRet = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userRet);
	}

	int retryCount = 1;
	// single user get
	@GetMapping("/{userId}")
//	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
//	@Retry(name = "ratingHotelService" , fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name = "userRateLimiter" , fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
		logger.info("retry count {}", retryCount++);
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	
	// creating fallback method for circuit breaker
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
		logger.info("fallack is executed because service is down : ", ex.getMessage());
		User user = User.builder()
		.email("dummy@gmail.com")
		.name("Dummy")
		.userId("123456")
		.about("this user is created as the rating service is down")
		.build();
		return ResponseEntity.ok(user);
	}

	// all users get
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUser();
		return ResponseEntity.ok(users);
	}
}
