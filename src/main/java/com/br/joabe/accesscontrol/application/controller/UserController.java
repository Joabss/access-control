package com.br.joabe.accesscontrol.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.joabe.accesscontrol.application.dto.LoginDTO;
import com.br.joabe.accesscontrol.application.service.UserService;
import com.br.joabe.accesscontrol.domain.model.User;
import com.br.joabe.accesscontrol.exception.BusinessRulesException;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody LoginDTO dto) {

		User usuario = User.builder()
				.name(dto.getName())
				.email(dto.getEmail())
				.password(dto.getPassword())
				.build();

		try {
			User usuarioSalvo = service.save(usuario);
			return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
		} catch (BusinessRulesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<Object> buscar() {
		List<User> users = service.findAll();
		return ResponseEntity.ok(users);

	}

}
