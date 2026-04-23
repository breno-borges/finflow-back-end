package br.com.brenoborges.finflow.utils.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/")
    @Operation(summary = "Disponibilidade da API", description = "Essa funcao e responsavel por validar se a API está disponível")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionando"),
            @ApiResponse(responseCode = "400", description = "Não funcionou")
    })
    public ResponseEntity<Object> health() {

        try {
            return ResponseEntity.ok().body("Funcionando");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não funcionou");
        }
    }
}
