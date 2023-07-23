package com.gabrielbrazdev.minhasfinancas.service;

import java.util.Optional;

import com.gabrielbrazdev.minhasfinancas.model.entity.User;

public interface UserService {
	
	User authenticate(String email, String password);
	
	User saveUser(User user);
	
	void validateEmail(String email);
	
	

}
