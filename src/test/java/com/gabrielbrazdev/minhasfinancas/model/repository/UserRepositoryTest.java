package com.gabrielbrazdev.minhasfinancas.model.repository;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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

	@Autowired
	TestEntityManager entityManager;

	@DisplayName("Verify if email exists")
	@Test
	public void verifyEmailExists() {
		// cenário
		User user = criarUser();
		entityManager.persist(user);

		// ação/ execução
		boolean result = repository.existsByEmail("usuario@email.com");

		// verificacao
		Assertions.assertThat(result).isTrue();
	}

	@Test
	public void devePersistirUmUserNaBaseDeDados() {
		// cenário
		User usuario = criarUser();

		// acao
		User usuarioSalvo = repository.save(usuario);

		// verificacao
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}

	@Test
	public void deveBuscarUmUserPorEmail() {
		// cenario
		User usuario = criarUser();
		entityManager.persist(usuario);

		// verificacao
		Optional<User> result = repository.findByEmail("usuario@email.com");

		Assertions.assertThat(result.isPresent()).isTrue();

	}

	@Test
	public void deveRetornarVazioAoBuscarUserPorEmailQuandoNaoExisteNaBase() {

		// verificacao
		Optional<User> result = repository.findByEmail("usuario@email.com");

		Assertions.assertThat(result.isPresent()).isFalse();
	}

	public static User criarUser() {
		return User.builder().nome("usuario").email("usuario@email.com").senha("senha").build();
	}

}
