package sg.edu.nus.ad_backend.service.impl;

import org.springframework.stereotype.Service;
import sg.edu.nus.ad_backend.model.Transaction;
import sg.edu.nus.ad_backend.repository.TransactionRepository;
import sg.edu.nus.ad_backend.service.ITransactionService;

import java.util.List;

@Service
public class TransactionService implements ITransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public Void deleteTransactionById(Long id) {
        repository.deleteById(id);
        return null;
    }
}
