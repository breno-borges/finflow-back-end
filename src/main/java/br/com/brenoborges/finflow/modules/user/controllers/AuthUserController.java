package br.com.brenoborges.finflow.modules.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brenoborges.finflow.modules.user.dtos.LoginRequestDTO;
import br.com.brenoborges.finflow.modules.user.dtos.AccessTokenDTO;
import br.com.brenoborges.finflow.modules.user.dtos.ForgotPasswordRequestDTO;
import br.com.brenoborges.finflow.modules.user.useCases.AuthTokenUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/user")
public class AuthUserController {

    @Autowired
    private AuthTokenUseCase authTokenUseCase;

    @PostMapping("/login")
    @Operation(summary = "Login do usuário", description = "Essa funcao e responsavel por gerar o token de acesso do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token de acesso"),
            @ApiResponse(responseCode = "401", description = "Acesso negado")
    })
    public ResponseEntity<Object> auth(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            AccessTokenDTO token = this.authTokenUseCase.loginToken(loginRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/forgotPassword")
    @Operation(summary = "Token de recovery de senha", description = "Essa funcao e responsavel por gerar o token de recovery de senha do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token de acesso"),
            @ApiResponse(responseCode = "400", description = "Usuário não encontrado")
    })
    public ResponseEntity<Object> auth(@RequestBody ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
        try {
            AccessTokenDTO token = this.authTokenUseCase.forgotPassword(forgotPasswordRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
