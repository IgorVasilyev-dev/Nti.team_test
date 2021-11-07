package by.nti_team.test_work.error.advice;


import by.nti_team.test_work.error.ApiError;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object>  handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return  new ResponseEntity<>(new ApiError(status, "missing required parameter", ex.getMessage()), status);
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    protected ResponseEntity<?> handlerEmptyResultDataAccessException(EmptyResultDataAccessException exception) {
        return ResponseEntity.badRequest().body(new ApiError(HttpStatus.NOT_FOUND,
                "Not found Entity", exception.getMessage()));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<?> handlerIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(new ApiError(HttpStatus.BAD_REQUEST,
                "Not Found Entity", exception.getMessage()));
    }


    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<?> handlerEntityNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity.badRequest().body(new ApiError(HttpStatus.NOT_FOUND,
                "Not Found Entity", exception.getMessage()));
    }

    @ExceptionHandler(value = EntityExistsException.class)
    protected ResponseEntity<?> handlerEntityExistsException(EntityExistsException exception) {
        return ResponseEntity.badRequest().body(new ApiError(HttpStatus.BAD_REQUEST,
                "Entity Already Exists", exception.getMessage()));
    }


}
