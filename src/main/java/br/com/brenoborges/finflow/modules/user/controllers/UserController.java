package br.com.brenoborges.finflow.modules.user.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.brenoborges.finflow.modules.user.dtos.ProfileRequestDTO;
import br.com.brenoborges.finflow.modules.user.dtos.ProfileResponseDTO;
import br.com.brenoborges.finflow.modules.user.dtos.UpdateUserRequestDTO;
import br.com.brenoborges.finflow.modules.user.entities.UserEntity;
import br.com.brenoborges.finflow.modules.user.useCases.CreateUserUseCase;
import br.com.brenoborges.finflow.modules.user.useCases.ResetPasswordUseCase;
import br.com.brenoborges.finflow.modules.user.useCases.UpdateUserUseCase;
import br.com.brenoborges.finflow.modules.user.useCases.UserProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private UserProfileUseCase userProfileUseCase;

    @Autowired
    private UpdateUserUseCase updateUserUseCase;

    @Autowired
    private ResetPasswordUseCase resetPasswordUseCase;

    @PostMapping("/signUp")
    @Operation(summary = "Cadastro do usuário", description = "Essa funcao e responsavel por cadastrar as informacoes do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Usuário ja existe")
    })
    public ResponseEntity<Object> signUp(@Valid @RequestBody ProfileRequestDTO profileRequestDTO) {
        UserEntity newUser = new UserEntity(profileRequestDTO);

        try {
            this.createUserUseCase.execute(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/profile")
    @Operation(summary = "Perfil do usuário", description = "Essa funcao e responsavel por buscar as informacoes do perfil do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getProfile(HttpServletRequest request) {

        Object idUser = request.getAttribute("id_user");

        try {
            ProfileResponseDTO profile = this.userProfileUseCase.execute(UUID.fromString(idUser.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado!");
        }
    }

    @PutMapping("/update/")
    @Operation(summary = "Atualização do usuário", description = "Essa funcao e responsavel por atualizar as informacoes do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", description = "Usuário não encontrado")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> updateUser(HttpServletRequest request,
            @Valid @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {

        Object idUser = request.getAttribute("id_user");

        try {
            this.updateUserUseCase.execute(UUID.fromString(idUser.toString()), updateUserRequestDTO);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/resetPassword")
    @Operation(summary = "Reset de senha", description = "Essa funcao e responsavel por resetar a senha do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "E-mail de redefinição enviado"),
            @ApiResponse(responseCode = "400", description = "Usuário não encontrado")
    })
    public ResponseEntity<Object> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            this.resetPasswordUseCase.resetPassword(token, newPassword);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
