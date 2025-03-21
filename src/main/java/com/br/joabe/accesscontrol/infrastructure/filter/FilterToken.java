package com.br.joabe.accesscontrol.infrastructure.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.br.joabe.accesscontrol.application.service.TokenService;
import com.br.joabe.accesscontrol.domain.model.User;
import com.br.joabe.accesscontrol.domain.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterToken extends OncePerRequestFilter {

	@Autowired
	private TokenService tokeService;

	@Autowired
	private UserRepository repository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token;
		var authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null) {
			token = authorizationHeader.split(" ")[1];
			var subject = this.tokeService.getSubject(token);

			User user = repository.findByEmail(subject)
					.orElseThrow(() -> new UsernameNotFoundException("Email não cadastrado."));

			var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);

		}

		filterChain.doFilter(request, response);

	}

}
