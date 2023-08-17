package sg.edu.nus.ad_backend.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.ad_backend.common.ApplicationConstants;
import sg.edu.nus.ad_backend.common.BookConstants;
import sg.edu.nus.ad_backend.dto.CompleteApplicationDTO;
import sg.edu.nus.ad_backend.model.Application;
import sg.edu.nus.ad_backend.model.Book;
import sg.edu.nus.ad_backend.model.Member;
import sg.edu.nus.ad_backend.service.IApplicationService;
import sg.edu.nus.ad_backend.service.IBookService;
import sg.edu.nus.ad_backend.service.IMemberService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {
    private final IApplicationService applicationService;
    private final IMemberService memberService;
    private final IBookService bookService;

    public ApplicationController(IApplicationService applicationService,
                                 IMemberService memberService,
                                 IBookService bookService) {
        this.applicationService = applicationService;
        this.memberService = memberService;
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAll() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getOne(@PathVariable("id") Long id) {
        Application application = applicationService.getApplicationById(id);
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @PostMapping
    public ResponseEntity<Application> create(@RequestBody Application application) {
        return ResponseEntity.ok(applicationService.saveApplication(application));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> update(@PathVariable("id") Long id, @RequestBody Application application) {
        Application existing = applicationService.getApplicationById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        application.setId(id);
        return ResponseEntity.ok(applicationService.saveApplication(application));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Application existing = applicationService.getApplicationById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicationService.deleteApplicationById(id));
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<List<Application>> byMemberId(@PathVariable("id") Long id) {
        Member member = memberService.getMemberById(id);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicationService.getByMemberId(id));
    }

    @PutMapping("/approve/{id}")
    @Transactional
    public ResponseEntity<Void> approveApplication(@PathVariable("id") Long id) {
        Application application = applicationService.getApplicationById(id);
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        if (!Objects.equals(application.getStatus(), ApplicationConstants.APPLICATION_PENDING)) {
            return ResponseEntity.badRequest().build();
        }
        if (!Objects.equals(application.getBook().getStatus(), BookConstants.BOOK_AVAILABLE)) {
            return ResponseEntity.badRequest().build();
        }
        // update application status
        application.setStatus(ApplicationConstants.APPLICATION_APPROVED);
        applicationService.saveApplication(application);
        // auto update book status
        Book book = bookService.getBookById(application.getBook().getId());
        book.setStatus(BookConstants.BOOK_RESERVED);
        bookService.saveBook(book);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Void> rejectApplication(@PathVariable("id") Long id) {
        Application application = applicationService.getApplicationById(id);
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        if (!Objects.equals(application.getStatus(), ApplicationConstants.APPLICATION_PENDING)) {
            return ResponseEntity.badRequest().build();
        }
        application.setStatus(ApplicationConstants.APPLICATION_REJECTED);
        applicationService.saveApplication(application);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/ready/{id}")
    public ResponseEntity<Void> readyApplication(@PathVariable("id") Long id) {
        Application application = applicationService.getApplicationById(id);
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        if (!Objects.equals(application.getStatus(), ApplicationConstants.APPLICATION_APPROVED)) {
            return ResponseEntity.badRequest().build();
        }
        application.setStatus(ApplicationConstants.APPLICATION_READY_FOR_COLLECTION);
        applicationService.saveApplication(application);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/complete")
    @Transactional
    public ResponseEntity<Void> completeApplication(@RequestBody CompleteApplicationDTO dto) {
        Application application = applicationService.getByRecipientIdAndBookId(dto.getRecipientId(), dto.getBookId());
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        if (!Objects.equals(application.getStatus(), ApplicationConstants.APPLICATION_READY_FOR_COLLECTION)) {
            return ResponseEntity.badRequest().build();
        }
        if (!Objects.equals(application.getBook().getStatus(), BookConstants.BOOK_RESERVED)) {
            return ResponseEntity.badRequest().build();
        }
        // update application status
        application.setStatus(ApplicationConstants.APPLICATION_COMPLETED);
        applicationService.saveApplication(application);
        // auto update book status
        Book book = bookService.getBookById(application.getBook().getId());
        book.setStatus(BookConstants.BOOK_UNAVAILABLE);
        bookService.saveBook(book);
        // auto update receive count
        Member member = memberService.getMemberById(application.getRecipient().getId());
        member.setReceiveCount(member.getReceiveCount() + 1);
        memberService.saveMember(member);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cancel")
    @Transactional
    public ResponseEntity<Void> cancelApplication(@RequestBody CompleteApplicationDTO dto) {
        Application application = applicationService.getByRecipientIdAndBookId(dto.getRecipientId(), dto.getBookId());
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        if (!Objects.equals(application.getStatus(), ApplicationConstants.APPLICATION_READY_FOR_COLLECTION)) {
            return ResponseEntity.badRequest().build();
        }
        if (!Objects.equals(application.getBook().getStatus(), BookConstants.BOOK_RESERVED)) {
            return ResponseEntity.badRequest().build();
        }
        // update application status
        application.setStatus(ApplicationConstants.APPLICATION_CANCELLED);
        applicationService.saveApplication(application);
        // auto update book status
        Book book = bookService.getBookById(application.getBook().getId());
        book.setStatus(BookConstants.BOOK_AVAILABLE);
        bookService.saveBook(book);
        return ResponseEntity.ok().build();
    }
}
