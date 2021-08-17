package ru.fomin.nyakashop.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.fomin.nyakashop.dto.ErrorDto;
import ru.fomin.nyakashop.exceptions.AccessResourceDeniedException;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> catchResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(AccessResourceDeniedException.class)
    public ResponseEntity<?> accessResourceDeniedException(AccessResourceDeniedException ex) {
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

}
