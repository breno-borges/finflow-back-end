package br.com.brenoborges.finflow.modules.user.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

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

    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = RequiredMode.REQUIRED, description = "Id do usuario")
    private UUID idUser;
    @Schema(example = "Zezinho Santos", requiredMode = RequiredMode.REQUIRED, description = "Nome do usuario")
    private String name;
    @Schema(example = "email@email.com", requiredMode = RequiredMode.REQUIRED, description = "E-mail do usuario")
    private String email;
    @Schema(example = "22", description = "Idade do usuario")
    private int age;
    @Schema(example = "Masculino", description = "Genero do usuario")
    private String gender;
    @Schema(example = "2024-01-01T00:00:00", requiredMode = RequiredMode.REQUIRED, description = "Data de criação do usuario")
    private LocalDateTime createdAt;
}
