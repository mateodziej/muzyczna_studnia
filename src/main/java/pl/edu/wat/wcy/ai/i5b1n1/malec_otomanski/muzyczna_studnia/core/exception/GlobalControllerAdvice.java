package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public void entityNotFoundHandler(HttpServletResponse response, EntityNotFoundException e) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    void illegalArgumentHandler(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
