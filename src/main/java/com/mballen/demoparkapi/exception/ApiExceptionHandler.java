package com.mballen.demoparkapi.exception;

import com.mballen.demoparkapi.exceptionUsuario.PasswordInvalidException;
import com.mballen.demoparkapi.exceptionUsuario.UsernameUniqueViolationException;
import com.mballen.demoparkapi.exceptionUsuario.UsuarioNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMensage> errorMensageMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                     HttpServletRequest request,
                                                     BindingResult bindingResult){
        log.error("Api erro - ", exception);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMensage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) inválido(s)", bindingResult));
    }

    @ExceptionHandler(UsernameUniqueViolationException.class)
    public ResponseEntity<ErrorMensage> errorMensageUsernameUniqueViolationException(RuntimeException exception,
                                                                                     HttpServletRequest request){
        log.error("Api erro - ", exception );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMensage(request, HttpStatus.CONFLICT, exception.getMessage()));
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ErrorMensage> errorMensageUsuarioNotFoundException(RuntimeException exception,
                                                                             HttpServletRequest request){
        log.error("Api erro - ", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMensage(request, HttpStatus.NOT_FOUND, "User não encontrado!"));
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMensage> errorMensagePasswordInvalidException(RuntimeException exception, HttpServletRequest request){
        log.error("Api erro - ", exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMensage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));
    }
}
