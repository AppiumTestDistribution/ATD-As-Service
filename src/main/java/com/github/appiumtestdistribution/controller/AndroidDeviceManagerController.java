package com.github.appiumtestdistribution.controller;

import java.util.List;

import com.github.android.AndroidManager;
import com.github.appiumtestdistribution.error.NoDeviceFoundException;
import com.github.appiumtestdistribution.modal.DeviceInfo;
import com.github.appiumtestdistribution.modal.Devices;
import com.github.device.Device;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AndroidDeviceManagerController {
    private final AndroidManager manager;

    public AndroidDeviceManagerController() {
        this.manager = new AndroidManager();
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/devices/android/{udid}")
    public Device getAndroidDevice(@PathVariable final String udid) {
        try {
            return this.manager.getDevice(udid);
        } catch (final RuntimeException e) {
            throw new NoDeviceFoundException(udid);
        }
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/devices/android")
    public Devices getAndroidDevices() {
        final List<Device> devices = this.manager.getDevices();
        if (devices.size() == 0) {
            throw new NoDeviceFoundException();
        }
        return new Devices(devices);
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/devices/android/{udid}/info")
    public DeviceInfo getDeviceInfo(@PathVariable final String udid) {
        try {
            final JSONObject info = this.manager.getDeviceInfo(udid);
            return DeviceInfo.builder()
                .apiLevel(info.getString("apiLevel"))
                .screenSize(info.getString("screenSize"))
                .osVersion(info.getString("osVersion"))
                .os(info.getString("os"))
                .name(info.getString("name"))
                .isDevice(info.getBoolean("isDevice"))
                .deviceModel(info.getString("deviceModel"))
                .udid(info.getString("udid"))
                .deviceManufacturer(info.getString("deviceManufacturer"))
                .brand(info.getString("brand"))
                .build();
        } catch (final ArrayIndexOutOfBoundsException e) {
            throw new NoDeviceFoundException(udid);
        }
    }
}