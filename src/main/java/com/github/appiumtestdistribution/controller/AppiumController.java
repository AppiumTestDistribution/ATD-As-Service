package com.github.appiumtestdistribution.controller;


import com.github.appiumtestdistribution.modal.AppiumServerRequest;
import com.github.appiumtestdistribution.modal.AppiumServerResponse;
import com.github.appiumtestdistribution.service.AppiumService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppiumController {
    @Autowired
    private AppiumService service;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/appium/start")
    public AppiumServerResponse startAppiumServer(@RequestBody AppiumServerRequest appiumServerRequest) {
        return service.startAppiumServer(appiumServerRequest);
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/appium/stop")
    public String stopAppiumServer(@RequestBody AppiumServerRequest appiumServerRequest) {
        return service.stopAppiumServer();
    }

}
