package com.thihahtetkyaw.packages.controller;

import com.thihahtetkyaw.packages.dto.GeneralDto;
import com.thihahtetkyaw.packages.exception.PackageException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(name = "packagesExceptionHandlers")
public class ExceptionHandlers {

    @ExceptionHandler(PackageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralDto<?> businessLayerHandler(PackageException e){
        var logger = LoggerFactory.getLogger(e.getClass());
        logger.error("", e);
        return GeneralDto.withBusinessError("Package error", e.getMessage());
    }

}
