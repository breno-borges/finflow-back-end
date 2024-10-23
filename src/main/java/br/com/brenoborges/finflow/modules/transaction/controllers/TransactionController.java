package br.com.brenoborges.finflow.modules.transaction.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brenoborges.finflow.modules.transaction.dtos.NewTransactionRequestDTO;
import br.com.brenoborges.finflow.modules.transaction.useCases.NewTransactionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class TransactionController {

    @Autowired
    private NewTransactionUseCase newTransactionUseCase;

    @PostMapping("/newTransaction")
    @Operation(summary = "Transação", description = "Essa funcao e responsavel criar uma transação financeira")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Usuário não encontrado!")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request,
            @RequestBody NewTransactionRequestDTO newTransactionRequestDTO) {

        Object idUser = request.getAttribute("id_user");

        try {
            this.newTransactionUseCase.execute(UUID.fromString(idUser.toString()), newTransactionRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
