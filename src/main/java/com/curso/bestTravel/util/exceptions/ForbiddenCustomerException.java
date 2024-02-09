package com.curso.bestTravel.util.exceptions;

public class ForbiddenCustomerException extends RuntimeException{

    public ForbiddenCustomerException(){
        super("This customer is block");
    }
}
