package com.gabrielbrazdev.minhasfinancas.excepitions;

public class AuthErrorException extends RuntimeException{
	public AuthErrorException(String msg) {
		super(msg);
	}
}
