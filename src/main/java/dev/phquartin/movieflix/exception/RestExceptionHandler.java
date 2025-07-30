package dev.phquartin.movieflix.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss | dd/MM/yyyy");

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(500)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(formatter))
                .build();

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    // Erro de login
    @ExceptionHandler (org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(org.springframework.security.authentication.BadCredentialsException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Invalid username or password")
                .status(401)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(formatter))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // Quando a pagina nao for encontrada (Nao existir uma rota)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Page not found")
                .status(404)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(formatter))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Manipulador para exceções de validação (@Valid).
     * Retorna um corpo de resposta com os erros específicos de cada campo.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpServletRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(Objects.requireNonNull(e.getFieldError()).getDefaultMessage())
                .status(400)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(formatter))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceAlreadyExistsException e, HttpServletRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(409)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(formatter))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException e, HttpServletRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(404)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(formatter))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
