package com.gabrielbrazdev.minhasfinancas.service;

import com.gabrielbrazdev.minhasfinancas.model.entity.User;

public interface UserSerevice {
	
	User authenticate(String email, String password);
	
	User saveUser(User user);
	
	void validateEmail(String email);

}
