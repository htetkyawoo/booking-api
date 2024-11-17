package com.thihahtetkyaw.user.controller;

import com.thihahtetkyaw.user.dto.GeneralDto;
import com.thihahtetkyaw.user.exception.PasswordMissMatchException;
import com.thihahtetkyaw.user.exception.UserException;
import com.thihahtetkyaw.user.exception.ValidationFailureException;
import jakarta.persistence.PersistenceException;
import org.hibernate.boot.beanvalidation.IntegrationException;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;

@RestControllerAdvice(name = "userExceptionHandler")
public class ExceptionHandlers {

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralDto<?> businessLayerHandler(UserException e){
        var logger = LoggerFactory.getLogger(e.getClass());
        logger.error("", e);
        return GeneralDto.withBusinessError("User Error", e.getMessage());
    }

    @ExceptionHandler({IntegrationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralDto<?> integrationLayerHandler(IntegrationException e){
        var logger = LoggerFactory.getLogger(e.getClass().getName());
        logger.error("", e);
        return GeneralDto.withIntegrationError("Third-parties api Error", e.getMessage());
    }


    @ExceptionHandler({PersistenceException.class, SQLException.class, DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralDto<?> dataAccessLayerHandler(RuntimeException e){
        var logger = LoggerFactory.getLogger(e.getClass().getName());
        logger.error("", e);
        return GeneralDto.withDataLayerError("Data Access Error", e.getMessage());
    }

    @ExceptionHandler({AsyncRequestNotUsableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralDto<?> generalHandler(AsyncRequestNotUsableException e) {
        var logger = LoggerFactory.getLogger(e.getClass().getName());
        logger.error("", e);
        return GeneralDto.withGeneralError("Error","Sorry Something went wrong! Please try again later.");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GeneralDto<?> notFoundHandler(NoResourceFoundException ex) {
        var logger = LoggerFactory.getLogger(ex.getClass().getName());
        logger.error("", ex);
        return GeneralDto.withPresentationError("Url Not Found","Does not support /%s".formatted(ex.getResourcePath()));
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralDto generalHandler(Exception e) {
        var logger = LoggerFactory.getLogger(e.getClass().getName());
        logger.error("", e);
        return GeneralDto.withGeneralError("Error", "Sorry Something went wrong! Please try again later.");
    }


}
