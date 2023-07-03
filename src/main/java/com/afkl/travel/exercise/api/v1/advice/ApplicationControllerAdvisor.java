package com.afkl.travel.exercise.api.v1.advice;

import com.afkl.travel.exercise.exception.BusinessException;
import com.afkl.travel.exercise.exception.LocationNotFoundException;
import com.afkl.travel.exercise.model.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionDTO> handleBusinessException(BusinessException exception) {
        return new ResponseEntity<>(ExceptionDTO.builder()
                .message(exception.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleBusinessException(LocationNotFoundException exception) {
        return new ResponseEntity<>(ExceptionDTO.builder()
                .message(exception.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }
}