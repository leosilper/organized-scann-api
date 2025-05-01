package br.com.fiap.organized_scann_api.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fiap.organized_scann_api.model.User;
import br.com.fiap.organized_scann_api.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    // Lista de rotas públicas que não devem passar pelo filtro
    private static final List<String> EXCLUDED_PATHS = List.of(
        "/swagger-ui",
        "/swagger-ui/",
        "/swagger-ui.html",
        "/v3/api-docs",
        "/v3/api-docs/",
        "/v3/api-docs/swagger-config",
        "/auth/login",
        "/h2-console"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("🔐 AuthFilter executando...");

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            System.out.println("🚫 Header Authorization inválido ou ausente");
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "").trim();

        try {
            User user = tokenService.getUserFromToken(token);

            if (user != null) {
                var authentication = new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        null,
                        user.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("⚠️ Usuário não encontrado no token");
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao validar token: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                { "message": "Token inválido ou expirado" }
            """);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
