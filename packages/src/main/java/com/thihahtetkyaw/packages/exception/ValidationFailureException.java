package com.thihahtetkyaw.packages.exception;

import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

@Getter
public class ValidationFailureException extends PackageException {

    private final List<String> messages;

    public ValidationFailureException(BindingResult result) {
        messages = result.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
    }

    public ValidationFailureException(String ... message){
        messages = Arrays.stream(message).toList();
    }

    @Override
    public String getMessage() {
        String message = "";
        for(var m : messages) {
            message += m + "\n";
        }
        return message;
    }
}
