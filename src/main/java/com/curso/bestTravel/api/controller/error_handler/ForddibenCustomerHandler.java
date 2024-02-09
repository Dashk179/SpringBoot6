package com.curso.bestTravel.api.controller.error_handler;

import com.curso.bestTravel.api.models.responses.BaseErrorResponse;
import com.curso.bestTravel.api.models.responses.ErrorResponse;
import com.curso.bestTravel.util.exceptions.ForbiddenCustomerException;
import com.curso.bestTravel.util.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForddibenCustomerHandler {
    @ExceptionHandler(ForbiddenCustomerException.class)
    public BaseErrorResponse handleIdNotFount(ForbiddenCustomerException exception){
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.FORBIDDEN.name())
                .code(HttpStatus.FORBIDDEN.value())
                .build();
    }


}
