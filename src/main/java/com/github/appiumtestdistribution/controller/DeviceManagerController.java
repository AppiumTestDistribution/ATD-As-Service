package com.github.appiumtestdistribution.controller;

import java.util.List;

import com.github.device.Device;
import com.github.device.DeviceManager;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceManagerController {
    @SneakyThrows
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/devices")
    public List<Device> getDevices() {
        return new DeviceManager().getDevices();
    }
}