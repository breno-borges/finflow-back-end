package br.com.brenoborges.finflow.modules.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record UpdateUserRequestDTO(
        @Schema(example = "Zezinho Santos", requiredMode = RequiredMode.REQUIRED, description = "Nome do usuario") String name,
        @Schema(example = "22", description = "Idade do usuario") int age,
        @Schema(example = "Masculino", description = "Genero do usuario") String gender) {

}
