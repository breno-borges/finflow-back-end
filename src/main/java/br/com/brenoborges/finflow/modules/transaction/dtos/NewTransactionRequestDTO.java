package br.com.brenoborges.finflow.modules.transaction.dtos;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record NewTransactionRequestDTO(
        @Schema(example = "Desenvolvimento de site", requiredMode = RequiredMode.REQUIRED, description = "Descricao da transacao") String description,
        @Schema(example = "Venda", requiredMode = RequiredMode.REQUIRED, description = "Categoria da transacao") String category,
        @Schema(example = "250.00", requiredMode = RequiredMode.REQUIRED, description = "Valor da transacao") float value,
        @Schema(example = "Input", requiredMode = RequiredMode.REQUIRED, description = "Tipo de transação, se é entrada ou saída") String typeTransaction,
        @Schema(example = "2024-10-22", requiredMode = RequiredMode.REQUIRED, description = "Data da transacao") LocalDate date) {

}
