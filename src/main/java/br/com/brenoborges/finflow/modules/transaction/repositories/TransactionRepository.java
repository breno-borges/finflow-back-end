package br.com.brenoborges.finflow.modules.transaction.repositories;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.brenoborges.finflow.modules.transaction.entities.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    Page<TransactionEntity> findAllByIdUserAndDescriptionContaining(UUID idUser, String description, Pageable pageable);

    Page<TransactionEntity> findAllByIdUserAndDescriptionContainingAndDateTransactionBetween(UUID idUser,
            String description,
            Pageable pageable,
            LocalDate startDate, LocalDate endDate);

    List<TransactionEntity> findAllByIdUser(UUID idUser);

    List<TransactionEntity> findAllByIdUserAndDateTransactionBetween(UUID idUser,
            LocalDate startDate, LocalDate endDate);
}
