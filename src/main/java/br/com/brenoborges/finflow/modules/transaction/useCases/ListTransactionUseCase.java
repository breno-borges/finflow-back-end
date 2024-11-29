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
import br.com.brenoborges.finflow.modules.transaction.entities.Category;
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
            String endDateString) {

        this.userRepository.findById(idUser)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "dateTransaction"));
        Page<TransactionEntity> transactions;

        if (startDateString != "" && endDateString != "") {
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = LocalDate.parse(endDateString);

            transactions = this.transactionRepository.findAllByIdUserAndDateTransactionBetween(idUser, pageable,
                    startDate,
                    endDate);
        } else {
            transactions = this.transactionRepository.findAllByIdUser(idUser, pageable);
        }

        var transactionsCreditAmount = transactions.getContent().stream()
                .filter(t -> "credit".equalsIgnoreCase(t.getTypeTransaction()))
                .mapToDouble(TransactionEntity::getValueTransaction)
                .sum();
        var transactionsDebitAmount = transactions.getContent().stream()
                .filter(t -> "debit".equalsIgnoreCase(t.getTypeTransaction()))
                .mapToDouble(TransactionEntity::getValueTransaction)
                .sum();

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

        Category category1 = new Category("Receitas");
        Category category2 = new Category("Reservas Financeiras");
        Category category3 = new Category("Despesas Gerais");
        Category category4 = new Category("Custos e Despesas Fixos");
        Category category5 = new Category("Custos e Despesas Variáveis");

        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);

        return CategoryTransactionsDTO.builder()
                .categories(categories).build();

    }

}
