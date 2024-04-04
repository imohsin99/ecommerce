package com.example.ecommerce.exception;

import com.example.ecommerce.constant.ConstantMessage;
import com.example.ecommerce.response.EcommerceResponse;
import com.example.ecommerce.response.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private EcommerceResponse<String> ecommerceResponse;

  @Order(value = 1)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<EcommerceResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    ecommerceResponse = new EcommerceResponse<>();
    ecommerceResponse.addError(ConstantMessage.INVALID_REQUEST_STRUCTURE);
    return new ResponseEntity<>(ecommerceResponse, HttpStatus.BAD_REQUEST);
  }

  @Order(value = 1)
  @ExceptionHandler({MissingServletRequestPartException.class, MissingServletRequestParameterException.class})
  public ResponseEntity<EcommerceResponse<String>> handleMissingServletRequestPartAndMissingServletRequestParameterExceptions(Exception e) {
    ecommerceResponse = new EcommerceResponse<>();
    ecommerceResponse.addError(ConstantMessage.EXPECTED_REQUEST_PARAMETERS_NOT_PROVIDED);
    return new ResponseEntity<>(ecommerceResponse, HttpStatus.BAD_REQUEST);
  }

  @Order(value = 2)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<EcommerceResponse<String>> handleMethodArgumentsNotValidException(MethodArgumentNotValidException e) {
    ecommerceResponse = new EcommerceResponse<>();
    ecommerceResponse.setErrors(formatBindingResult(e.getBindingResult()));
    return new ResponseEntity<>(ecommerceResponse, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  private List<ErrorResponse> formatBindingResult(BindingResult bindingResult) {
    return bindingResult
            .getFieldErrors()
            .stream()
            .map(fieldError -> new ErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()))
            .toList();
  }

  @Order(value = 2)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<EcommerceResponse<String>> handleConstraintViolationException(ConstraintViolationException e) {
    ecommerceResponse = new EcommerceResponse<>();
    ecommerceResponse.setErrors(formatConstraintViolations(e.getConstraintViolations()));
    return new ResponseEntity<>(ecommerceResponse, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  private List<ErrorResponse> formatConstraintViolations(Set<ConstraintViolation<?>> constraintViolations) {
    return constraintViolations.stream()
            .map(constraintViolation -> new ErrorResponse(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()))
            .toList();
  }

  @Order(value = 2)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<EcommerceResponse<String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
    ecommerceResponse = new EcommerceResponse<>();
    ecommerceResponse.addError(ConstantMessage.NUMBER_FORMAT_EXCEPTION_MESSAGE);
    return new ResponseEntity<>(ecommerceResponse, HttpStatus.BAD_REQUEST);
  }

  @Order(value = 3)
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<EcommerceResponse<String>> handleRuntimeException(RuntimeException e) {
    ecommerceResponse = new EcommerceResponse<>();
    if (e instanceof TransactionSystemException) {
      ConstraintViolationException cve = (ConstraintViolationException) ((TransactionSystemException) e).getRootCause();
      ecommerceResponse.setErrors(formatConstraintViolations(requireNonNull(cve).getConstraintViolations()));
      return new ResponseEntity<>(ecommerceResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    ecommerceResponse.addError(ConstantMessage.INTERNAL_ERROR);
    return new ResponseEntity<>(ecommerceResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
