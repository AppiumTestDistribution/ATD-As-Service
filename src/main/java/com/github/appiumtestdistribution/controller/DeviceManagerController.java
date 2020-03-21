package com.github.appiumtestdistribution.controller;

import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;

import java.util.List;
import java.util.stream.Collectors;

import com.github.appiumtestdistribution.error.NoDeviceFoundException;
import com.github.appiumtestdistribution.modal.Device;
import com.github.appiumtestdistribution.modal.Devices;
import com.github.device.DeviceManager;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceManagerController {
    private final MongoOperations mongoOperation;

    public DeviceManagerController() {
        final String path = format("/{0}/src/main/resources/SpringConfig.xml", getProperty("user.dir"));
        final ApplicationContext ctx = new GenericXmlApplicationContext(path);
        this.mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/devices/allocate")
    public void allocateDevice() {
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("devices/freeDevice")
    public void freeDevice() {
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/devices/{udid}")
    public Device getDevice(@PathVariable final String udid) {
        try {
            return new Device(new DeviceManager().getDevice(udid));
        } catch (final RuntimeException e) {
            throw new NoDeviceFoundException(udid);
        }
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/devices")
    public Devices getDevices() {
        final List<Device> devices = new DeviceManager().getDevices()
            .stream()
            .map(Device::new)
            .collect(Collectors.toList());
        if (devices.size() == 0) {
            throw new NoDeviceFoundException();
        }
        devices.forEach(this.mongoOperation::save);
        return new Devices(devices);
    }
}