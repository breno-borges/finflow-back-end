package br.com.brenoborges.finflow.modules.user.dtos;

public record ProfileRequestDTO(
                String name,
                String email,
                int age,
                String password,
                String gender) {
}
