package br.com.brenoborges.finflow.modules.transaction.dtos;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListTransactionsResponseDTO {
        private UUID idTransaction;
        @Schema(example = "Desenvolvimento de site", requiredMode = RequiredMode.REQUIRED, description = "Descricao da transacao")
        private String description;
        @Schema(example = "250.00", requiredMode = RequiredMode.REQUIRED, description = "Valor da transacao")
        private float valueTransaction;
        @Schema(example = "Venda", requiredMode = RequiredMode.REQUIRED, description = "Categoria da transacao")
        private String category;
        @Schema(example = "2024-10-22", requiredMode = RequiredMode.REQUIRED, description = "Data da transacao")
        private LocalDate dateTransaction;
        @Schema(example = "Input", requiredMode = RequiredMode.REQUIRED, description = "Tipo de transação, se é entrada ou saída")
        private String typeTransaction;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private UUID idUser;
        @Schema(example = "Zezinho Santos", requiredMode = RequiredMode.REQUIRED, description = "Nome do usuário")
        private String nameUser;
}
