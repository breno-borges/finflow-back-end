package br.com.brenoborges.finflow.modules.transaction.useCases;

import java.util.UUID;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.brenoborges.finflow.exceptions.UserNotFoundException;
import br.com.brenoborges.finflow.modules.transaction.entities.TransactionEntity;
import br.com.brenoborges.finflow.modules.transaction.repositories.TransactionRepository;
import br.com.brenoborges.finflow.modules.user.repositories.UserRepository;

@Service
public class ListTransactionUseCase {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<TransactionEntity> execute(UUID idUser, int page, int limit, String startDateString,
            String endDateString) {

        this.userRepository.findById(idUser)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        Pageable pageable = PageRequest.of(page, limit);

        if (startDateString != null && endDateString != null) {
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = LocalDate.parse(endDateString);

            return this.transactionRepository.findAllByIdUserAndDateTransactionBetween(idUser, pageable, startDate,
                    endDate);
        }

        return this.transactionRepository.findAllByIdUser(idUser, pageable);
    }
}
