package com.curso.bestTravel.api.controller.error_handler;

import com.curso.bestTravel.util.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)

public class BadResquestController {

    @ExceptionHandler(IdNotFoundException.class)
    public String handleIdNotFount(IdNotFoundException exception){
        return exception.getMessage();
    }
}
