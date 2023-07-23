package com.gabrielbrazdev.minhasfinancas.service.impl;


import org.springframework.stereotype.Service;

import com.gabrielbrazdev.minhasfinancas.excepitions.RuleBusinessException;
import com.gabrielbrazdev.minhasfinancas.model.entity.User;
import com.gabrielbrazdev.minhasfinancas.model.repository.UserRepository;
import com.gabrielbrazdev.minhasfinancas.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository repository;

	public UserServiceImpl(UserRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public User authenticate(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
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
