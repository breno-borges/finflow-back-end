package br.com.brenoborges.finflow.modules.transaction.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.brenoborges.finflow.modules.transaction.dtos.NewTransactionRequestDTO;
import br.com.brenoborges.finflow.modules.user.entities.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "transactions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idTransaction;

    @Schema(example = "Desenvolvimento de site", requiredMode = RequiredMode.REQUIRED, description = "Descricao da transacao")
    private String description;

    @Schema(example = "250.00", requiredMode = RequiredMode.REQUIRED, description = "Valor da transacao")
    private float valueTransaction;

    @Schema(example = "Venda", requiredMode = RequiredMode.REQUIRED, description = "Categoria da transacao")
    private String category;

    @Schema(example = "07/10/2024", requiredMode = RequiredMode.REQUIRED, description = "Data da transacao")
    private LocalDate dateTransaction;

    @Schema(example = "Input", requiredMode = RequiredMode.REQUIRED, description = "Tipo de transação, se é entrada ou saída")
    private String typeTransaction;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    private UserEntity userEntity;

    @Column(name = "id_user", nullable = false)
    private UUID idUser;

    public TransactionEntity(NewTransactionRequestDTO newTransactionRequestDTO) {
        this.description = newTransactionRequestDTO.description();
        this.category = newTransactionRequestDTO.category();
        this.valueTransaction = newTransactionRequestDTO.value();
        this.dateTransaction = newTransactionRequestDTO.date();
        this.typeTransaction = newTransactionRequestDTO.typeTransaction();
    }

    public String getFormatedDate(){
        return this.dateTransaction.toString();
    }

}
