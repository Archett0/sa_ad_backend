package sg.edu.nus.ad_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.ad_backend.model.BookTimestamp;
import sg.edu.nus.ad_backend.service.IBookTimestampService;

import java.util.List;

@RestController
@RequestMapping("/api/bookTimestamp")
public class BookTimestampController {
    private final IBookTimestampService bookTimestampService;

    public BookTimestampController(IBookTimestampService bookTimestampService) {
        this.bookTimestampService = bookTimestampService;
    }

    @GetMapping
    public ResponseEntity<List<BookTimestamp>> getAll() {
        return ResponseEntity.ok(bookTimestampService.getAllBookTimestamps());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookTimestamp> getOne(@PathVariable("id") Long id) {
        BookTimestamp bookTimestamp = bookTimestampService.getBookTimestampById(id);
        if (bookTimestamp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookTimestamp);
    }

    @PostMapping
    public ResponseEntity<BookTimestamp> create(@RequestBody BookTimestamp bookTimestamp) {
        return ResponseEntity.ok(bookTimestampService.saveBookTimestamp(bookTimestamp));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookTimestamp> update(@PathVariable("id") Long id, @RequestBody BookTimestamp bookTimestamp) {
        BookTimestamp existing = bookTimestampService.getBookTimestampById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        bookTimestamp.setId(id);
        return ResponseEntity.ok(bookTimestampService.saveBookTimestamp(bookTimestamp));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        BookTimestamp existing = bookTimestampService.getBookTimestampById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookTimestampService.deleteBookTimestampById(id));
    }
}
