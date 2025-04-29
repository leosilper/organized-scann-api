package br.com.fiap.organized_scann_api.model;

public record Token(
    String token,
    Long expiration,
    String type,
    String role
) {}
