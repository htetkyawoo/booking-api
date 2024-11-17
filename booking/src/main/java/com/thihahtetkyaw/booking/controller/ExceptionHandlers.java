package com.thihahtetkyaw.booking.controller;

import com.thihahtetkyaw.booking.exception.BookingException;
import com.thihahtetkyaw.booking.exception.CountryMissMatchException;
import com.thihahtetkyaw.booking.exception.OverLappingException;
import com.thihahtetkyaw.packages.dto.GeneralDto;
import com.thihahtetkyaw.packages.exception.PackagesExpireException;
import com.thihahtetkyaw.packages.exception.PurchaseException;
import com.thihahtetkyaw.packages.exception.ValidationFailureException;
import jakarta.persistence.PersistenceException;
import org.hibernate.boot.beanvalidation.IntegrationException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice(name = "bookingExceptionHandler")
public class ExceptionHandlers {

    @ExceptionHandler(BookingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralDto<?> businessLayerHandler(BookingException e){
        var logger = LoggerFactory.getLogger(e.getClass());
        logger.error("", e);
        return GeneralDto.withBusinessError("Booking Error", e.getMessage());
    }

}
