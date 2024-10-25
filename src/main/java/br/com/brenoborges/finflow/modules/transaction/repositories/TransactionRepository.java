package br.com.brenoborges.finflow.modules.transaction.repositories;

import java.util.UUID;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.brenoborges.finflow.modules.transaction.entities.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    Page<TransactionEntity> findAllByIdUser(UUID idUser, Pageable pageable);

    Page<TransactionEntity> findAllByIdUserAndDateTransactionBetween(UUID idUser, Pageable pageable,
            LocalDate startDate, LocalDate endDate);
}
