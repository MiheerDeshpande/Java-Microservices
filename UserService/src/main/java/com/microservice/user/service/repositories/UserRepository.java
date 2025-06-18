package com.microservice.user.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.user.service.entities.User;

public interface UserRepository extends JpaRepository<User, String>{
	
// this is to implement any custom method or query 
//    @Query("SELECT u FROM User u WHERE u.email = :email")
//    Optional<User> findUserByEmail(@Param("email") String email);
	
	
}
