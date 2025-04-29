package br.com.fiap.organized_scann_api.controller;

import br.com.fiap.organized_scann_api.model.Credentials;
import br.com.fiap.organized_scann_api.model.Token;
import br.com.fiap.organized_scann_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Token login(@RequestBody @Valid Credentials credentials) {
        log.info("Attempting login with: {}", credentials);
        return authService.login(credentials);
    }

}
