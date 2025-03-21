package com.br.joabe.accesscontrol.application.controller;

import com.br.joabe.accesscontrol.application.dto.LoginDTO;
import com.br.joabe.accesscontrol.application.service.TokenService;
import com.br.joabe.accesscontrol.domain.model.User;
import com.br.joabe.accesscontrol.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(),
                        dto.getPassword());
        try {
            Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            var user = (User) authenticate.getPrincipal();

            String token = tokenService.generateToken(user);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
