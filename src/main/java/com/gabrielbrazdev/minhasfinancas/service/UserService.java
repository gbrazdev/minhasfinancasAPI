package com.gabrielbrazdev.minhasfinancas.service;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gabrielbrazdev.minhasfinancas.model.entity.User;


@Service 
@Component
public interface UserService {
	
	User authenticate(String email, String password);
	
	User saveUser(User user);
	
	void validateEmail(String email);
	
	

}
