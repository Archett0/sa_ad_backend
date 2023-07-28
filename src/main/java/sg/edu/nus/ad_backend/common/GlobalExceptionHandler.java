package sg.edu.nus.ad_backend.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Intercept all exceptions
     *
     * @param e Exception
     * @return Common response
     */
    @ExceptionHandler
    public ResponseEntity<String> handlerException(Exception e) {
        // Print exception stack
        e.printStackTrace();
        // Generate failed response
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
