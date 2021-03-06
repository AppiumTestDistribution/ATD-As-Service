package com.github.appiumtestdistribution.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.appiumtestdistribution.error.NoDeviceFoundException;
import com.github.appiumtestdistribution.modal.Device;
import com.github.appiumtestdistribution.modal.DeviceRepository;
import com.github.appiumtestdistribution.modal.Devices;
import com.github.device.DeviceManager;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceManagerController {
    @Autowired
    private DeviceRepository repository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/devices/{uuid}/allocate")
    public Device allocateDevice(@PathVariable final String uuid) {
        return toggleDevice(uuid, true);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/devices/{uuid}/freeDevice")
    public Device freeDevice(@PathVariable final String uuid) {
        return toggleDevice(uuid, false);
    }

    @Deprecated
    @SneakyThrows
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/devices/{udid}")
    public Device getDevice(@PathVariable final String udid) {
        try {
            return this.repository.findDeviceByUdid(udid);
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
        devices.forEach(this.repository::save);
        return new Devices(devices);
    }

    private Device toggleDevice(final String uuid, final boolean allocate) {
        if (this.repository.findAll()
            .size() == 0) {
            getDevices();
        }
        final Optional<Device> device = Optional.ofNullable(this.repository.findDeviceByUdid(uuid));
        device.ifPresent(value -> value.setAvailable(!allocate));
        device.orElseThrow(NoDeviceFoundException::new);
        this.repository.save(device.get());
        return device.get();
    }
}