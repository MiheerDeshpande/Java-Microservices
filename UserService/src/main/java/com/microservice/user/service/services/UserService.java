package com.microservice.user.service.services;

import java.util.List;

import com.microservice.user.service.entities.User;

public interface UserService {

	//define the operations for the userServicce implementation class
	
	//usually the structure of CRUD exist here
	//Create
	User saveUser(User user);
	
	//get All user
	List<User> getAllUser();
	
	//get single user
	User getUser(String userId);
	
}
