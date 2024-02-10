package com.curso.bestTravel.api.controller.error_handler;

import com.curso.bestTravel.api.models.responses.BaseErrorResponse;
import com.curso.bestTravel.api.models.responses.ErrorResponse;
import com.curso.bestTravel.api.models.responses.ErrorsResponse;
import com.curso.bestTravel.util.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)

public class BadResquestController {

    @ExceptionHandler(IdNotFoundException.class)
    public BaseErrorResponse handleIdNotFount(IdNotFoundException exception){
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handleIdNotFount(MethodArgumentNotValidException exception){
        var erros = new ArrayList<String>();
        exception.getAllErrors()
                .forEach(error -> erros.add(error.getDefaultMessage()));
return ErrorsResponse.builder()
        .errors(erros)
        .status(HttpStatus.BAD_REQUEST.name())
        .code(HttpStatus.BAD_REQUEST.value())
        .build();

    }
}
