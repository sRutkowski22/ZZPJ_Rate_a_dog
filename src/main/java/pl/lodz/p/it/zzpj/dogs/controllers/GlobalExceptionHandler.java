package pl.lodz.p.it.zzpj.dogs.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.lodz.p.it.zzpj.dogs.exceptions.AppBaseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AppBaseException.class})
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
