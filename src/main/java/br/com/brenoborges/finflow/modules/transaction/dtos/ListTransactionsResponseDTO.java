package br.com.brenoborges.finflow.modules.transaction.dtos;

import java.util.List;

import br.com.brenoborges.finflow.modules.transaction.entities.TransactionEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListTransactionsResponseDTO {
        @Schema()
        private List<TransactionEntity> data; // resumo e dados
        @Schema()
        private double transactionsAmount;
        @Schema()
        private double creditTransactionsAmount;
        @Schema()
        private double debitTransactionsAmount;
        @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Pagina Atual")
        private int page;
        @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Total de paginas")
        private int total;
        @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Ultima pagina")
        private int lastPage;
}
