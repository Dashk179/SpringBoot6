package com.curso.bestTravel.infraestructure.helpers;

import com.curso.bestTravel.util.exceptions.ForbiddenCustomerException;
import org.springframework.stereotype.Component;

@Component
public class BlackListHelper {
    public void isInBlackListCutomer(String customerID){
        if (customerID .equals("GOTW771012HMRGR087")){
            throw  new ForbiddenCustomerException();
        }
    }
}
