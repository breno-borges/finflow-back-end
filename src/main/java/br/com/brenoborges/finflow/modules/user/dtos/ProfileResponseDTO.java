package br.com.brenoborges.finflow.modules.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDTO {

    @Schema(example = "Zezinho Santos", requiredMode = RequiredMode.REQUIRED, description = "Nome do usuario")
    private String name;
    @Schema(example = "email@email.com", requiredMode = RequiredMode.REQUIRED, description = "E-mail do usuario")
    private String email;
    @Schema(example = "22", description = "Idade do usuario")
    private int age;
    @Schema(example = "Masculino", description = "Genero do usuario")
    private String gender;
}
