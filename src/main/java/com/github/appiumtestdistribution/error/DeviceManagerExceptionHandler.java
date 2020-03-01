package com.github.appiumtestdistribution.error;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DeviceManagerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoDeviceFoundException.class)
    public void handleNoDeviceFound(final HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}