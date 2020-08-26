package com.github.appiumtestdistribution.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class AppiumServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppiumServiceNotFoundException.class)
    public void handleAppiumServiceNotFound(final HttpServletResponse response) throws IOException{
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

}