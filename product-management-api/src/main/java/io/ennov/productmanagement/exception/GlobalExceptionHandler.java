package io.ennov.productmanagement.exception;

import io.ennov.productmanagement.exception.error.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Calendar;


/**
 * @author : t.randrianarisoa
 * @project : product-manager application
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Custom exception handler for ResourceNotFoundException/UsernameNotFoundException/BadCredentialsException/NoHandlerFoundException
    @ExceptionHandler({ProductNotFoundException.class, ResourceNotFoundException.class, UsernameNotFoundException.class, BadCredentialsException.class, NoHandlerFoundException.class})
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.error(HttpStatus.NOT_FOUND + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(Calendar.getInstance().getTime())
                .message(ex.getMessage())
                .description(request.getDescription(Boolean.FALSE))
                .build());
    }

    //When given wrong request param
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.error(HttpStatus.METHOD_NOT_ALLOWED + ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ErrorMessage.builder()
                .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
                .timestamp(Calendar.getInstance().getTime())
                .message(ex.getMessage())
                .description(request.getDescription(Boolean.FALSE))
                .build());
    }

    //When given invalid values to request body
    @ExceptionHandler({HttpMessageNotReadableException.class, IllegalArgumentException.class, ServiceException.class})
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        log.error(HttpStatus.BAD_REQUEST + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(Calendar.getInstance().getTime())
                .message(ex.getMessage())
                .description(request.getDescription(Boolean.FALSE))
                .build());
    }


    //Invalid media types
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorMessage> handleUnsupportedMediaTypeException(HttpMediaTypeNotSupportedException ex, WebRequest request) {
        log.error(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE), ex.getContentType());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(ErrorMessage.builder()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .timestamp(Calendar.getInstance().getTime())
                .message(ex.getMessage())
                .description(request.getDescription(Boolean.FALSE))
                .build()
        );
    }
}
