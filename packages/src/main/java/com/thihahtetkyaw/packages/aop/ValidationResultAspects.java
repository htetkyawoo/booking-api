package com.thihahtetkyaw.packages.aop;

import com.thihahtetkyaw.packages.exception.ValidationFailureException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;

@Configuration("packagesBindingResult")
@Aspect
public class ValidationResultAspects {

    @Before(value = "within(com.thihahtetkyaw.packages.controller.*) && args(*,result,..)", argNames = "result")
    public void checkResult(BindingResult result){
        if(result.hasFieldErrors()){
            result.getAllErrors().forEach(System.out::println);
            throw new ValidationFailureException(result);
        }
    }
}
