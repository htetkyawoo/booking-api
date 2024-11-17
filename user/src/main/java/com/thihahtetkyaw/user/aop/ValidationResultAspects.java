package com.thihahtetkyaw.user.aop;

import com.thihahtetkyaw.user.exception.ValidationFailureException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;

@Configuration("userBindingResult")
@Aspect
public class ValidationResultAspects {

    @Before(value = "within(com.thihahtetkyaw.user.controller.*) && args(*,result,..)", argNames = "result")
    public void checkResult(BindingResult result){
        if(result.hasFieldErrors()){
            result.getAllErrors().forEach(System.out::println);
            throw new ValidationFailureException(result);
        }
    }
}
