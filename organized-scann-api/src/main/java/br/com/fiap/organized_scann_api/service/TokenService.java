package br.com.fiap.organized_scann_api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import br.com.fiap.organized_scann_api.model.Token;
import br.com.fiap.organized_scann_api.model.User;
import br.com.fiap.organized_scann_api.model.UserRole;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class TokenService {

    private static final Long DURATION = 10L; // duração do token em minutos
    private static final Algorithm ALG = Algorithm.HMAC256("secret"); // chave secreta para assinar o token

    public Token createToken(User user) {
        String token = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().toString())
                .withExpiresAt(LocalDateTime.now()
                        .plusMinutes(DURATION)
                        .toInstant(ZoneOffset.ofHours(-3)))
                .sign(ALG);

        return new Token(token, 21315656L, "Bearer", user.getRole().toString());
    }

    public User getUserFromToken(String token) {
        var decoded = JWT.require(ALG)
                .build()
                .verify(token);

        return User.builder()
                .id(Long.parseLong(decoded.getSubject()))
                .email(decoded.getClaim("email").asString())
                .role(UserRole.valueOf(decoded.getClaim("role").asString()))
                .build();
    }
}
