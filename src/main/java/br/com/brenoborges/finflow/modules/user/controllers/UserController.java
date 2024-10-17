package br.com.brenoborges.finflow.modules.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brenoborges.finflow.modules.user.dtos.ProfileRequestDTO;
import br.com.brenoborges.finflow.modules.user.entities.UserEntity;
import br.com.brenoborges.finflow.modules.user.useCases.CreateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @PostMapping("/signUp")
    @Operation(summary = "Cadastro do usuário", description = "Essa funcao e responsavel por cadastrar as informacoes do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Usuário ja existe")
    })
    public ResponseEntity<Object> signIn(@Valid @RequestBody ProfileRequestDTO profileRequestDTO) {
        UserEntity newUser = new UserEntity(profileRequestDTO);

        try {
            this.createUserUseCase.execute(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
