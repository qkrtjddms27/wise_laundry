package com.ssafy.wiselaundry.global.model.exception;

import com.ssafy.wiselaundry.global.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

import static com.ssafy.wiselaundry.global.model.exception.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            ConstraintViolationException.class,
            DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleDataDuplicateException() {
        log.error("DataException throw Exception : {}", DUPLICATE_RESOURCE);
        return ErrorResponse.toResponseEntity(DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseEntity<ErrorResponse> handleDataNotFoundException() {
        log.error("EntityNotFoundException throw CustomException : {}", DATA_NOT_FOUND);
        return ErrorResponse.toResponseEntity(DATA_NOT_FOUND);
    }
}
