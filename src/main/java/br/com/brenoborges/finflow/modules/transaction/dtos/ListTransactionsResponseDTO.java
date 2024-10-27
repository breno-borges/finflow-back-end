package br.com.brenoborges.finflow.modules.transaction.dtos;

import java.util.UUID;

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
        private String description;
        private float valueTransaction;
        private String category;
        private LocalDate dateTransaction;
        private String typeTransaction;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private UUID idUser;
        private String nameUser;
}
