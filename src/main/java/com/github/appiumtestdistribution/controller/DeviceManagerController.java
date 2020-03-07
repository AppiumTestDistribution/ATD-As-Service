package com.github.appiumtestdistribution.controller;

import java.util.List;

import com.github.appiumtestdistribution.error.NoDeviceFoundException;
import com.github.appiumtestdistribution.modal.Devices;
import com.github.device.Device;
import com.github.device.DeviceManager;
import com.github.manager.AppiumDevice;
import com.github.manager.AppiumDeviceManager;
import com.github.manager.DeviceAllocationManager;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceManagerController {
    private final DeviceAllocationManager allocationManager;

    public DeviceManagerController() {
        this.allocationManager = DeviceAllocationManager.getInstance();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/devices/allocate")
    public AppiumDevice allocateDevice() {
        this.allocationManager.allocateDevice(this.allocationManager.getNextAvailableDevice());
        return AppiumDeviceManager.getAppiumDevice();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("devices/freeDevice")
    public AppiumDevice freeDevice() {
        this.allocationManager.freeDevice();
        return AppiumDeviceManager.getAppiumDevice();
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/devices/{udid}")
    public Device getDevice(@PathVariable final String udid) {
        try {
            return new DeviceManager().getDevice(udid);
        } catch (final RuntimeException e) {
            throw new NoDeviceFoundException(udid);
        }
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/devices")
    public Devices getDevices() {
        final List<Device> devices = new DeviceManager().getDevices();
        if (devices.size() == 0) {
            throw new NoDeviceFoundException();
        }
        return new Devices(devices);
    }
}