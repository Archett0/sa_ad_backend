package sg.edu.nus.ad_backend.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sg.edu.nus.ad_backend.model.Book;
import sg.edu.nus.ad_backend.model.BookTimestamp;
import sg.edu.nus.ad_backend.security.principal.UserPrincipal;
import sg.edu.nus.ad_backend.security.token.JwtDecoder;
import sg.edu.nus.ad_backend.security.token.JwtToPrincipalConverter;
import sg.edu.nus.ad_backend.service.IBookService;
import sg.edu.nus.ad_backend.service.IBookTimestampService;
import sg.edu.nus.ad_backend.util.RoleIdByString;

import java.time.LocalDateTime;

/**
 * Intercept Book status changes, and record the changes as audit records
 */
@Aspect
@Component
public class BookStatusChangeAspect {
    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;
    private final IBookService bookService;
    private final IBookTimestampService service;

    public BookStatusChangeAspect(JwtDecoder jwtDecoder,
                                  JwtToPrincipalConverter jwtToPrincipalConverter,
                                  IBookService bookService,
                                  IBookTimestampService service) {
        this.jwtDecoder = jwtDecoder;
        this.jwtToPrincipalConverter = jwtToPrincipalConverter;
        this.bookService = bookService;
        this.service = service;
    }

    @Pointcut(value = "execution(* sg.edu.nus.ad_backend.controller.BookController.update(..)) && args(id, updatedBook, request)", argNames = "id,updatedBook,request")
    public void updateBookPointcut(Long id, Book updatedBook, HttpServletRequest request) {
    }

    @Around(value = "updateBookPointcut(id, updatedBook, request)", argNames = "joinPoint,id,updatedBook,request")
    public Object interceptUpdateBook(ProceedingJoinPoint joinPoint, Long id, Book updatedBook, HttpServletRequest request) throws Throwable {
        // Fetch the existing book
        Book existingBook = bookService.getBookById(id);
        // Compare status field
        if (!existingBook.getStatus().equals(updatedBook.getStatus())) {
            // Parse user info from JWT token
            String bearerToken = request.getHeader("Authorization");
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                String token = bearerToken.substring(7);
                UserPrincipal userPrincipal = jwtToPrincipalConverter.convert(jwtDecoder.decode(token));
                String roles = userPrincipal.displayRoles();
                BookTimestamp bookTimestamp = new BookTimestamp(existingBook,
                        existingBook.getStatus(),
                        updatedBook.getStatus(),
                        LocalDateTime.now(),
                        RoleIdByString.getRoleType(roles),
                        userPrincipal.getUserId(),
                        userPrincipal.getUsername());
                service.saveBookTimestamp(bookTimestamp);
            }
        }
        return joinPoint.proceed();
    }
}
