package sg.edu.nus.ad_backend.service;

import sg.edu.nus.ad_backend.model.Transaction;

import java.util.List;

public interface ITransactionService {
    List<Transaction> getAllTransactions();
    Transaction getTransactionById(Long id);
    Transaction saveTransaction(Transaction transaction);
    Void deleteTransactionById(Long id);
}
