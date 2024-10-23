package br.com.brenoborges.finflow.modules.transaction.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.brenoborges.finflow.modules.transaction.entities.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

}
