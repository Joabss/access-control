package com.br.joabe.accesscontrol.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TokenDTO {

	private Long id;
	private String name;
	private String token;
}
