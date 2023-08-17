package sg.edu.nus.ad_backend.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.ad_backend.common.BookConstants;
import sg.edu.nus.ad_backend.model.Book;
import sg.edu.nus.ad_backend.model.Member;
import sg.edu.nus.ad_backend.service.IBookService;
import sg.edu.nus.ad_backend.service.IMemberService;

import java.util.List;
import java.util.Objects;

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
    @Transactional
    public ResponseEntity<Book> update(@PathVariable("id") Long id, @RequestBody Book book) {
        Book existing = bookService.getBookById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        book.setId(id);
        // if status changed
        if (!Objects.equals(existing.getStatus(), book.getStatus())) {
            if (existing.getStatus().equals(BookConstants.BOOK_DEPOSITED) && book.getStatus().equals(BookConstants.BOOK_AVAILABLE)) {
                Member member = memberService.getMemberById(book.getDonor().getId());
                member.setDonationCount(member.getDonationCount() + 1);
                memberService.saveMember(member);
            }
        }
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

    @GetMapping("/recipient/{id}")
    public ResponseEntity<List<Book>> byRecipientId(@PathVariable("id") Long id) {
        Member member = memberService.getMemberById(id);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookService.getByRecipientId(id));
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<Book> likeBook(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        book.setLikeCount(book.getLikeCount() + 1);
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @GetMapping("/completedList/{id}")
    public ResponseEntity<List<Book>> completedByRecipientId(@PathVariable("id") Long id) {
        Member member = memberService.getMemberById(id);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookService.getCompletedByRecipientId(id));
    }

    @PutMapping("/deposit/{id}")
    public ResponseEntity<Void> confirmDeposit(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        if (!Objects.equals(book.getStatus(), BookConstants.BOOK_PENDING)) {
            return ResponseEntity.badRequest().build();
        }
        book.setStatus(BookConstants.BOOK_DEPOSITED);
        bookService.saveBook(book);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Void> rejectBook(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        if (!Objects.equals(book.getStatus(), BookConstants.BOOK_DEPOSITED)) {
            return ResponseEntity.badRequest().build();
        }
        book.setStatus(BookConstants.BOOK_REJECTED);
        bookService.saveBook(book);
        return ResponseEntity.ok().build();
    }
}
