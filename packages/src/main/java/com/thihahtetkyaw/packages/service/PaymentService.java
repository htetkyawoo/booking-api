package com.thihahtetkyaw.packages.service;

import com.thihahtetkyaw.packages.exception.PurchaseException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    public boolean AddPaymentCard(String cardDetails)
    {
        return true;
    }

    public boolean isEnough(String cardDetails, BigDecimal amount){
        return true;
    }

    public boolean PaymentCharge(String cardDetails, BigDecimal amount)
    {
        if(isEnough(cardDetails, amount)){
            return true;
        }
        throw new PurchaseException("Please topping up the card and try again.");
    }

}
