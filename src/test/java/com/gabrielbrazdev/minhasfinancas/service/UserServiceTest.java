package com.gabrielbrazdev.minhasfinancas.service;

import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gabrielbrazdev.minhasfinancas.excepitions.AuthError;
import com.gabrielbrazdev.minhasfinancas.excepitions.RuleBusinessException;
import com.gabrielbrazdev.minhasfinancas.model.entity.User;
import com.gabrielbrazdev.minhasfinancas.model.repository.UserRepository;
import com.gabrielbrazdev.minhasfinancas.service.impl.UserServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")

class UserServiceTest {

	@SpyBean
	UserServiceImpl service;

	@MockBean
	UserRepository repository;
	
	@Before("@annotation(runAs)")
	public void setup() {
		repository = Mockito.mock(UserRepository.class);
		
		service = new UserServiceImpl(repository);
	}

	@Test
	public void deveSalvarUmUser() {
		// cenário
		Mockito.doNothing().when(service).validateEmail(null);
		User user = User.builder().id(1l).nome("nome").email("email@email.com").senha("senha").build();

		Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);

		// acao
		User UserSalvo = service.saveUser(new User());

		// verificao
		Assertions.assertThat(UserSalvo).isNotNull();
		Assertions.assertThat(UserSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(UserSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(UserSalvo.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(UserSalvo.getSenha()).isEqualTo("senha");

	}

	@Test
	public void naoDeveSalvarUmUserComEmailJaCadastrado() {
		// cenario
		String email = "email@email.com";
		User user = User.builder().email(email).build();
		Mockito.doThrow(RuleBusinessException.class).when(service).validateEmail(email);

		// acao
		org.junit.jupiter.api.Assertions.assertThrows(RuleBusinessException.class, () -> service.saveUser(user));

		// verificacao
		Mockito.verify(repository, Mockito.never()).save(user);
	}

	@Test
	public void deveAutenticarUmUserComSucesso() {
		// cenário
		String email = "email@email.com";
		String senha = "senha";

		User user = User.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(user));

		// acao
		User result = service.authenticate(email, senha);

		// verificacao
		Assertions.assertThat(result).isNotNull();

	}

	@Test
	public void deveLancarErroQUandoNaoEncontrarUserCadastradoComOEmailInformado() {

		// cenário
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

		// acao
		Throwable exception = Assertions.catchThrowable(() -> service.authenticate("email@email.com", "senha"));

		// verificacao
		Assertions.assertThat(exception).isInstanceOf(AuthError.class)
				.hasMessage("Usuário não encontrado para o email informado.");
	}

	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
		// cenario
		String senha = "senha";
		User user = User.builder().email("email@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

		// acao
		Throwable exception = Assertions.catchThrowable(() -> service.authenticate("email@email.com", "123"));
		Assertions.assertThat(exception).isInstanceOf(AuthError.class).hasMessage("Senha inválida.");

	}

	@Test
	public void deveValidarEmail() {
		// cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

		// acao
		service.validateEmail("email@email.com");
	}

	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		// cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

		// acao
		org.junit.jupiter.api.Assertions.assertThrows(RuleBusinessException.class,
				() -> service.validateEmail("email@email.com"));
	}

}
