package com.gabrielbrazdev.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gabrielbrazdev.minhasfinancas.model.entity.User;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {
	
	@Autowired
	UserRepository repository;
	
	
	
	@Test
	public void verifyEmailExists() {
		// Cenary
		User user = User.builder().nome("usuario").email("usuario@email.com").build();
		repository.save(user);

		// Run
		boolean result = repository.existsByEmail("usuario@email.com");

		// Verify
		Assertions.assertThat(result).isTrue();
	}


	

}
