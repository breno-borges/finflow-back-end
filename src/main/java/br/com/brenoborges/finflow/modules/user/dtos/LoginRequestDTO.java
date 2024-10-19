package br.com.brenoborges.finflow.modules.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record LoginRequestDTO(
                @Schema(example = "email@email.com", requiredMode = RequiredMode.REQUIRED, description = "E-mail do usuario") String email,
                @Schema(example = "123456", minLength = 6, requiredMode = RequiredMode.REQUIRED, description = "Senha do usuario") String password) {
}
