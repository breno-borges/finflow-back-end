package br.com.brenoborges.finflow.modules.transaction.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brenoborges.finflow.exceptions.UserNotFoundException;
import br.com.brenoborges.finflow.modules.transaction.dtos.NewTransactionRequestDTO;
import br.com.brenoborges.finflow.modules.transaction.entities.TransactionEntity;
import br.com.brenoborges.finflow.modules.transaction.repositories.TransactionRepository;
import br.com.brenoborges.finflow.modules.user.repositories.UserRepository;

@Service
public class NewTransactionUseCase {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TransactionEntity execute(UUID idUser, NewTransactionRequestDTO newTransactionRequestDTO) {

        this.userRepository.findById(idUser)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        TransactionEntity transaction = new TransactionEntity(newTransactionRequestDTO);
        transaction.setIdUser(idUser);

        return this.transactionRepository.save(transaction);
    }
}
