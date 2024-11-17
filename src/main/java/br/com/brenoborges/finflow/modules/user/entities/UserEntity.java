package br.com.brenoborges.finflow.modules.user.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.brenoborges.finflow.modules.user.dtos.ProfileRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUser;

    @Schema(example = "Zezinho Santos", requiredMode = RequiredMode.REQUIRED, description = "Nome do usuario")
    private String name;

    @Schema(example = "email@email.com", requiredMode = RequiredMode.REQUIRED, description = "E-mail do usuario")
    private String email;

    @Schema(example = "22", description = "Idade do usuario")
    private int age;

    @Schema(example = "123456", minLength = 6, requiredMode = RequiredMode.REQUIRED, description = "Senha do usuario")
    private String password;

    @Schema(example = "Masculino", description = "Genero do usuario")
    private String gender;

    @Schema(description = "Token para redefinir a senha")
    private String resetPasswordToken;

    @Schema(example = "true", description = "Verificador de atividade do usuario")
    private boolean isActive;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public UserEntity(ProfileRequestDTO profileRequestDTO) {
        this.idUser = profileRequestDTO.idUser();
        this.name = profileRequestDTO.name();
        this.age = profileRequestDTO.age();
        this.email = profileRequestDTO.email();
        this.gender = profileRequestDTO.gender();
        this.password = profileRequestDTO.password();
        this.createdAt = profileRequestDTO.createdAt();
    }

}
