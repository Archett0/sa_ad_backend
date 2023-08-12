package sg.edu.nus.ad_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.ad_backend.model.Book;
import sg.edu.nus.ad_backend.model.Member;
import sg.edu.nus.ad_backend.service.IBookService;
import sg.edu.nus.ad_backend.service.IMemberService;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final IBookService bookService;
    private final IMemberService memberService;

    public BookController(IBookService bookService, IMemberService memberService) {
        this.bookService = bookService;
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getOne(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping("/donor/{memberId}")
    public ResponseEntity<List<Book>> getBooksByDonorId(@PathVariable("memberId") Long memberId) {
        Member member = memberService.getMemberById(memberId);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookService.getBooksByMemberId(memberId));
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable("id") Long id, @RequestBody Book book) {
        Book existing = bookService.getBookById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        book.setId(id);
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Book existing = bookService.getBookById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookService.deleteBookById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> search(@RequestParam String searchString) {
        return ResponseEntity.ok(bookService.searchBooksByTitle(searchString));
    }

    @GetMapping("/random")
    public ResponseEntity<List<Book>> getRandom() {
        return ResponseEntity.ok(bookService.getRandomBooks());
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<Book>> recommend() {
        return ResponseEntity.ok(bookService.getRandomBooks());
    }
}
