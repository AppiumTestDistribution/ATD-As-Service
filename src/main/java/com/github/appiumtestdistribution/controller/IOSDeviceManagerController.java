package com.github.appiumtestdistribution.controller;

import java.util.List;

import com.github.appiumtestdistribution.error.NoDeviceFoundException;
import com.github.appiumtestdistribution.modal.DeviceInfo;
import com.github.appiumtestdistribution.modal.Devices;
import com.github.device.Device;
import com.github.ios.IOSManager;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IOSDeviceManagerController {
    private final IOSManager manager;

    public IOSDeviceManagerController() {
        this.manager = new IOSManager();
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/devices/ios/{udid}/info")
    public DeviceInfo getDeviceInfo(@PathVariable final String udid) {
        try {
            final Device info = this.manager.getDevice(udid);
            return DeviceInfo.builder()
                .apiLevel(info.getApiLevel())
                .screenSize(info.getScreenSize())
                .osVersion(info.getOsVersion())
                .os(info.getOs())
                .name(info.getName())
                .isDevice(info.isDevice())
                .deviceModel(info.getDeviceModel())
                .udid(info.getUdid())
                .deviceManufacturer(info.getDeviceManufacturer())
                .brand(info.getBrand())
                .build();
        } catch (final RuntimeException e) {
            throw new NoDeviceFoundException(udid);
        }
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/devices/ios/{udid}")
    public Device getIosDevice(@PathVariable final String udid) {
        try {
            return this.manager.getDevice(udid);
        } catch (final RuntimeException e) {
            throw new NoDeviceFoundException(udid);
        }
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/devices/ios")
    public Devices getIosDevices() {
        final List<Device> devices = this.manager.getDevices();
        if (devices.size() == 0) {
            throw new NoDeviceFoundException();
        }
        return new Devices(devices);
    }
}
