package br.com.brenoborges.finflow.modules.transaction.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.brenoborges.finflow.modules.transaction.dtos.CategoryTransactionsDTO;
import br.com.brenoborges.finflow.modules.transaction.dtos.ListTransactionsResponseDTO;
import br.com.brenoborges.finflow.modules.transaction.dtos.NewTransactionRequestDTO;
import br.com.brenoborges.finflow.modules.transaction.useCases.DeleteTransactionUseCase;
import br.com.brenoborges.finflow.modules.transaction.useCases.ListTransactionUseCase;
import br.com.brenoborges.finflow.modules.transaction.useCases.NewTransactionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class TransactionController {

    @Autowired
    private NewTransactionUseCase newTransactionUseCase;

    @Autowired
    private DeleteTransactionUseCase deleteTransactionUseCase;

    @Autowired
    private ListTransactionUseCase listTransactionUseCase;

    @PostMapping("/newTransaction")
    @Operation(summary = "Criar Transação", description = "Essa funcao e responsavel criar uma transação financeira")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Usuário não encontrado!")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> newTransaction(HttpServletRequest request,
            @RequestBody NewTransactionRequestDTO newTransactionRequestDTO) {

        Object idUser = request.getAttribute("id_user");

        try {
            this.newTransactionUseCase.execute(UUID.fromString(idUser.toString()), newTransactionRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/transaction/delete")
    @Operation(summary = "Exclusão da transação", description = "Essa funcao e responsavel por excluir uma transação")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", description = "Transação não encontrada")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> deleteTransaction(@RequestParam("id_transaction") UUID idTransaction) {

        try {
            this.deleteTransactionUseCase.execute(idTransaction);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/transaction/findAllWithPagination")
    @Operation(summary = "Transações do usuário", description = "Essa funcao e responsavel por buscar todas as transações do usuário com paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ListTransactionsResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> findAllTransactionsWithPagination(HttpServletRequest request,
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String description) {

        Object idUser = request.getAttribute("id_user");

        try {
            ListTransactionsResponseDTO transactions = this.listTransactionUseCase.execute(
                    UUID.fromString(idUser.toString()), page, limit,
                    startDate, endDate, description);
            return ResponseEntity.ok().body(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado!");
        }
    }

    @GetMapping("/transaction/categories")
    @Operation(summary = "Categorias de Transacao", description = "Essa funcao e responsavel por buscar todas as categorias das transações")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CategoryTransactionsDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> findAllCategories(HttpServletRequest request) {

        Object idUser = request.getAttribute("id_user");

        try {
            CategoryTransactionsDTO categories = this.listTransactionUseCase.categories(
                    UUID.fromString(idUser.toString()));
            return ResponseEntity.ok().body(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado!");
        }
    }
}
