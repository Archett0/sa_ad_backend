package sg.edu.nus.ad_backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.ad_backend.common.ApplicationConstants;
import sg.edu.nus.ad_backend.common.BookConstants;
import sg.edu.nus.ad_backend.dto.CompleteApplicationDTO;
import sg.edu.nus.ad_backend.model.*;
import sg.edu.nus.ad_backend.security.principal.UserPrincipal;
import sg.edu.nus.ad_backend.security.token.JwtDecoder;
import sg.edu.nus.ad_backend.security.token.JwtToPrincipalConverter;
import sg.edu.nus.ad_backend.service.*;
import sg.edu.nus.ad_backend.util.RoleIdByString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {
    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;
    private final IApplicationService applicationService;
    private final IMemberService memberService;
    private final IBookService bookService;
    private final IBookTimestampService bookTimestampService;
    private final IApplicationTimestampService applicationTimestampService;

    public ApplicationController(JwtDecoder jwtDecoder,
                                 JwtToPrincipalConverter jwtToPrincipalConverter,
                                 IApplicationService applicationService,
                                 IMemberService memberService,
                                 IBookService bookService,
                                 IBookTimestampService bookTimestampService,
                                 IApplicationTimestampService applicationTimestampService) {
        this.jwtDecoder = jwtDecoder;
        this.jwtToPrincipalConverter = jwtToPrincipalConverter;
        this.applicationService = applicationService;
        this.memberService = memberService;
        this.bookService = bookService;
        this.bookTimestampService = bookTimestampService;
        this.applicationTimestampService = applicationTimestampService;
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
    public ResponseEntity<Void> approveApplication(@PathVariable("id") Long id, HttpServletRequest request) {
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
        // audit
        audit(application, ApplicationConstants.APPLICATION_PENDING, BookConstants.BOOK_AVAILABLE, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reject/{id}")
    @Transactional
    public ResponseEntity<Void> rejectApplication(@PathVariable("id") Long id, HttpServletRequest request) {
        Application application = applicationService.getApplicationById(id);
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        if (!Objects.equals(application.getStatus(), ApplicationConstants.APPLICATION_PENDING)) {
            return ResponseEntity.badRequest().build();
        }
        application.setStatus(ApplicationConstants.APPLICATION_REJECTED);
        applicationService.saveApplication(application);
        // audit
        audit(application, ApplicationConstants.APPLICATION_PENDING, -1, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/ready/{id}")
    @Transactional
    public ResponseEntity<Void> readyApplication(@PathVariable("id") Long id, HttpServletRequest request) {
        Application application = applicationService.getApplicationById(id);
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        if (!Objects.equals(application.getStatus(), ApplicationConstants.APPLICATION_APPROVED)) {
            return ResponseEntity.badRequest().build();
        }
        application.setStatus(ApplicationConstants.APPLICATION_READY_FOR_COLLECTION);
        applicationService.saveApplication(application);
        // audit
        audit(application, ApplicationConstants.APPLICATION_APPROVED, -1, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/complete")
    @Transactional
    public ResponseEntity<Void> completeApplication(@RequestBody CompleteApplicationDTO dto, HttpServletRequest request) {
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
        // audit
        audit(application, ApplicationConstants.APPLICATION_READY_FOR_COLLECTION, BookConstants.BOOK_RESERVED, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cancel")
    @Transactional
    public ResponseEntity<Void> cancelApplication(@RequestBody CompleteApplicationDTO dto, HttpServletRequest request) {
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
        // audit
        audit(application, ApplicationConstants.APPLICATION_READY_FOR_COLLECTION, BookConstants.BOOK_RESERVED, request);
        return ResponseEntity.ok().build();
    }

    private void audit(Application app, Integer prev, Integer bookPrev, HttpServletRequest request) {
        ApplicationTimestamp timestamp = new ApplicationTimestamp();
        timestamp.setApplication(app);
        timestamp.setStatusPrev(prev);
        timestamp.setStatusNow(app.getStatus());
        timestamp.setActionTime(LocalDateTime.now());

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            UserPrincipal userPrincipal = jwtToPrincipalConverter.convert(jwtDecoder.decode(token));
            timestamp.setRoleType(RoleIdByString.getRoleType(userPrincipal.displayRoles()));
            timestamp.setRoleId(userPrincipal.getUserId());
            timestamp.setRoleUsername(userPrincipal.getUsername());
            applicationTimestampService.saveApplicationTimestamp(timestamp);

            if (bookPrev != -1) {
                Book book = bookService.getBookById(app.getBook().getId());
                BookTimestamp bookTimestamp = new BookTimestamp();
                bookTimestamp.setBook(book);
                bookTimestamp.setStatusPrev(bookPrev);
                bookTimestamp.setStatusNow(book.getStatus());
                bookTimestamp.setActionTime(LocalDateTime.now());
                bookTimestamp.setRoleType(RoleIdByString.getRoleType(userPrincipal.displayRoles()));
                bookTimestamp.setRoleId(userPrincipal.getUserId());
                bookTimestamp.setRoleUsername(userPrincipal.getUsername());
                bookTimestampService.saveBookTimestamp(bookTimestamp);
            }
        }
    }
}
