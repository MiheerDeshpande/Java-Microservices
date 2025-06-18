package com.microservice.lcwd.hotel.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.lcwd.hotel.entities.Hotel;
import com.microservice.lcwd.hotel.exceptions.ResourceNotFoundException;
import com.microservice.lcwd.hotel.repositories.HotelRepository;
import com.microservice.lcwd.hotel.services.HotelService;

@Service
public class HotelServiceImpl implements HotelService{

	@Autowired
	private HotelRepository hotelRepository;

	@Override
	public Hotel create(Hotel hotel) {
		// TODO Auto-generated method stub
		String hotelId = UUID.randomUUID().toString();
		hotel.setId(hotelId);
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		// TODO Auto-generated method stub
		return hotelRepository.findAll();
	}

	@Override
	public Hotel get(String Id) {
		// TODO Auto-generated method stub
		return hotelRepository.findById(Id).orElseThrow(()-> new ResourceNotFoundException("hotel with given id not found"));
	}
}
