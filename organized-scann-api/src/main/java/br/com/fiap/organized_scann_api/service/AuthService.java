package br.com.fiap.organized_scann_api.service;

import br.com.fiap.organized_scann_api.model.Credentials;
import br.com.fiap.organized_scann_api.model.Token;
import br.com.fiap.organized_scann_api.model.User;
import br.com.fiap.organized_scann_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;

    public Token login(Credentials credentials) {
        User user = userRepository.findByEmail(credentials.email())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha inválidos"));

        if (!encoder.matches(credentials.password(), user.getPassword())) {
            throw new RuntimeException("E-mail ou senha inválidos");
        }

        return tokenService.createToken(user);
    }
}
