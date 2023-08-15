package sg.edu.nus.ad_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.ad_backend.model.Transaction;
import sg.edu.nus.ad_backend.service.ITransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final ITransactionService transactionService;

    public TransactionController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAll() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getOne(@PathVariable("id") Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transaction);
    }

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.saveTransaction(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> update(@PathVariable("id") Long id, @RequestBody Transaction transaction) {
        Transaction existing = transactionService.getTransactionById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        transaction.setId(id);
        return ResponseEntity.ok(transactionService.saveTransaction(transaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Transaction existing = transactionService.getTransactionById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactionService.deleteTransactionById(id));
    }
}
