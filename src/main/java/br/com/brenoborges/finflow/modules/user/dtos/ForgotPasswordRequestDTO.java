package br.com.brenoborges.finflow.modules.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;

public record ForgotPasswordRequestDTO(
        @Email(message = "O campo [email] deve conter um e-mail válido!") @Schema(example = "email@email.com", requiredMode = RequiredMode.REQUIRED, description = "E-mail do usuario") String email) {
}
