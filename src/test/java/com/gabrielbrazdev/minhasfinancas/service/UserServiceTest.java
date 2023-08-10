package com.gabrielbrazdev.minhasfinancas.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gabrielbrazdev.minhasfinancas.excepitions.AuthErrorException;
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

	@Test
	public void deveSalvarUmUser() {
		// cenário
		Mockito.doNothing().when(service).validateEmail(null);
		User usuario = User.builder().id(1l).nome("nome").email("email@email.com").senha("senha").build();

		Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(usuario);

		// acao
		User usuarioSalvo = service.saveUser(new User());

		// verificao
		Assertions.assertThat(usuarioSalvo).isNotNull();
		Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");

	}

	@Test
	public void naoDeveSalvarUmUserComEmailJaCadastrado() {
		// cenario
		String email = "email@email.com";
		User usuario = User.builder().email(email).build();
		Mockito.doThrow(RuleBusinessException.class).when(service).validateEmail(email);

		// acao
		org.junit.jupiter.api.Assertions.assertThrows(RuleBusinessException.class, () -> service.saveUser(usuario));

		// verificacao
		Mockito.verify(repository, Mockito.never()).save(usuario);
	}

	@Test
	public void deveAutenticarUmUserComSucesso() {
		// cenário
		String email = "email@email.com";
		String senha = "senha";

		User usuario = User.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

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
		Assertions.assertThat(exception).isInstanceOf(AuthErrorException.class)
				.hasMessage("Usuário não encontrado para o email informado.");
	}

	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
		// cenario
		String senha = "senha";
		User usuario = User.builder().email("email@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

		// acao
		Throwable exception = Assertions.catchThrowable(() -> service.authenticate("email@email.com", "123"));
		Assertions.assertThat(exception).isInstanceOf(AuthErrorException.class).hasMessage("Senha inválida.");

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
