package com.br.joabe.accesscontrol.exception;

import java.io.Serial;

public class BusinessRulesException extends RuntimeException {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public BusinessRulesException(String msg) {
		super(msg);
	}
}
