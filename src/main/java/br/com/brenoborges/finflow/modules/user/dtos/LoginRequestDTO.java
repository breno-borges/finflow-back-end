package br.com.brenoborges.finflow.modules.user.dtos;

public record LoginRequestDTO(
        String email,
        String password) {
}
