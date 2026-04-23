package br.com.brenoborges.finflow.modules.user.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;

public record ProfileRequestDTO(
        @Schema(example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = RequiredMode.REQUIRED, description = "Id do usuario") UUID idUser,
        @Schema(example = "Zezinho Santos", requiredMode = RequiredMode.REQUIRED, description = "Nome do usuario") String name,
        @Email(message = "O campo [email] deve conter um e-mail válido!") @Schema(example = "email@email.com", requiredMode = RequiredMode.REQUIRED, description = "E-mail do usuario") String email,
        @Schema(example = "22", description = "Idade do usuario") int age,
        @Length(min = 6, message = "A senha deve conter no minimo (6) caracteres") @Schema(example = "123456", minLength = 6, requiredMode = RequiredMode.REQUIRED, description = "Senha do usuario") String password,
        @Schema(example = "2024-01-01T00:00:00", requiredMode = RequiredMode.REQUIRED, description = "Data de criação do usuario") LocalDateTime createdAt,
        @Schema(example = "Masculino", description = "Genero do usuario") String gender) {
}
