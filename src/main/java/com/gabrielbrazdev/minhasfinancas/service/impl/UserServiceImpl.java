package com.gabrielbrazdev.minhasfinancas.service.impl;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gabrielbrazdev.minhasfinancas.excepitions.AuthError;
import com.gabrielbrazdev.minhasfinancas.excepitions.RuleBusinessException;
import com.gabrielbrazdev.minhasfinancas.model.entity.User;
import com.gabrielbrazdev.minhasfinancas.model.repository.UserRepository;
import com.gabrielbrazdev.minhasfinancas.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository repository;

	public UserServiceImpl(UserRepository repository) {
		super();
		this.repository = repository;
	}

	@Override	
	public User authenticate(String email, String password) {
		Optional<User> user = repository.findByEmail(email);
		
		if (!user.isPresent()) {			
			throw new AuthError("User not found");			
		}
		
		if (!user.get().getSenha().equals(password)) {
			throw new AuthError("Invalid password");	
		}
		return user.get();
	}

	@Override
	@Transactional
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validateEmail(String email) {
		boolean exists = repository.existsByEmail(email);
		if (exists) {
			throw new RuleBusinessException("Já Existe um usuário cadastrado com este email.");
		}

	}

}
