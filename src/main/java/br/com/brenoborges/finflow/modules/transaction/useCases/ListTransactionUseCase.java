package br.com.brenoborges.finflow.modules.transaction.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.brenoborges.finflow.exceptions.UserNotFoundException;
import br.com.brenoborges.finflow.modules.transaction.dtos.CategoryTransactionsDTO;
import br.com.brenoborges.finflow.modules.transaction.dtos.ListTransactionsResponseDTO;
import br.com.brenoborges.finflow.modules.transaction.entities.TransactionEntity;
import br.com.brenoborges.finflow.modules.transaction.repositories.TransactionRepository;
import br.com.brenoborges.finflow.modules.user.repositories.UserRepository;

@Service
public class ListTransactionUseCase {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public ListTransactionsResponseDTO execute(UUID idUser, int page, int limit, String startDateString,
            String endDateString, String description) {

        this.userRepository.findById(idUser)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "dateTransaction"));
        Page<TransactionEntity> transactions;
        List<TransactionEntity> transactionsList;

        if (startDateString != "" && endDateString != "") {
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = LocalDate.parse(endDateString);

            transactions = this.transactionRepository.findAllByIdUserAndDescriptionContainingAndDateTransactionBetween(
                    idUser,
                    description,
                    pageable,
                    startDate,
                    endDate);

            transactionsList = this.transactionRepository
                    .findAllByIdUserAndDescriptionContainingAndDateTransactionBetween(
                            idUser,
                            description,
                            startDate,
                            endDate);
        } else {
            transactions = this.transactionRepository.findAllByIdUserAndDescriptionContaining(idUser, description,
                    pageable);
            transactionsList = this.transactionRepository.findAllByIdUserAndDescriptionContaining(idUser, description);
        }

        var transactionsCreditAmount = 0;
        var transactionsDebitAmount = 0;

        for (TransactionEntity transaction : transactionsList) {
            if (transaction.getTypeTransaction().equalsIgnoreCase("credit")) {
                transactionsCreditAmount += transaction.getValueTransaction();
            } else {
                transactionsDebitAmount += transaction.getValueTransaction();
            }
        }

        var finalTransactionsAmount = transactionsCreditAmount - transactionsDebitAmount;
        return ListTransactionsResponseDTO.builder()
                .data(transactions.toList())
                .transactionsAmount(finalTransactionsAmount)
                .creditTransactionsAmount(transactionsCreditAmount)
                .debitTransactionsAmount(transactionsDebitAmount)
                .page(page + 1)
                .total(transactions.getNumberOfElements())
                .lastPage(transactions.getTotalPages())
                .build();
    }

    public CategoryTransactionsDTO categories(UUID idUser) {

        this.userRepository.findById(idUser)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        List<String> categories = new ArrayList<>();
        categories.add("Receitas");
        categories.add("Reservas Financeiras");
        categories.add("Despesas Gerais");
        categories.add("Custos e Despesas Fixos");
        categories.add("Custos e Despesas Variáveis");

        return CategoryTransactionsDTO.builder()
                .categories(categories).build();

    }

}
