package com.gabrielbrazdev.minhasfinancas.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gabrielbrazdev.minhasfinancas.model.repository.UserRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")

public class UserServiceTest {
	
	@Autowired
	
	UserService service;
	
	@Autowired
	UserRepository repository;
	
	@Test
	public void valitadeEmail() {
		
		repository.deleteAll();
		
		service.validateEmail("email@email.com");
	}
	
	
	

}
