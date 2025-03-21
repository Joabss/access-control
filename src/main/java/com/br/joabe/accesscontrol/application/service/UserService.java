package com.br.joabe.accesscontrol.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.joabe.accesscontrol.domain.model.User;
import com.br.joabe.accesscontrol.domain.repository.UserRepository;
import com.br.joabe.accesscontrol.exception.BusinessRulesException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	public User save(User user) {
		userValidation(user.getEmail());
		encryptPassword(user);
		return userRepository.save(user);
	}

	private void encryptPassword(User user) {
		String password = user.getPassword();
		String encryptedPassword = encoder.encode(password);
		user.setPassword(encryptedPassword);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public void userValidation(String email) {
		boolean existe = this.userRepository.existsByEmail(email);
		if (existe) {
			throw new BusinessRulesException("Já existe um usuário cadastrado com este email.");
		}
	}

}
