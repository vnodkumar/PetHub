package com.vk.PetHub.exception;

import com.vk.PetHub.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    //User Not Found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex){
        ErrorResponse error = new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    //User Already Exists
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex){
        ErrorResponse error = new ErrorResponse(ex.getMessage(),HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }

    //Product Not Found
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex){
        ErrorResponse error = new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
    //Product Already Exists
    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleProductAlreadyExists(ProductAlreadyExistsException ex){
        ErrorResponse error = new ErrorResponse(ex.getMessage(),HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }
    //Method args validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
        String cleanMessage = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();
        ErrorResponse error = new ErrorResponse(cleanMessage,HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(error);
    }
    //path variable validation
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handlePathVariableValidation(ConstraintViolationException ex) {
        // Extract the exact custom message (e.g., "User Id must be a positive number")
        String cleanMessage = ex.getConstraintViolations()
                .iterator()
                .next()
                .getMessage();

        ErrorResponse error = new ErrorResponse(cleanMessage, HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        ErrorResponse error = new ErrorResponse(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
