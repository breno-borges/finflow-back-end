package br.com.brenoborges.finflow.modules.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record ForgotPasswordDTO(
                @Schema(example = "test@test.comm", requiredMode = RequiredMode.REQUIRED, description = "email do usuario") String email) {
}
