package br.com.brenoborges.finflow.modules.transaction.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brenoborges.finflow.exceptions.TransactionNotFoundException;
import br.com.brenoborges.finflow.modules.transaction.repositories.TransactionRepository;

@Service
public class DeleteTransactionUseCase {

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void execute(UUID idTransaction) {
        this.transactionRepository.findById(idTransaction)
                .orElseThrow(() -> new TransactionNotFoundException());

        this.transactionRepository.deleteById(idTransaction);
    }
}
