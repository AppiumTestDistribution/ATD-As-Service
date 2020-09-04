package com.github.appiumtestdistribution.controller;

import com.github.device.Device;

public class DeviceBuilder {
    final Device device;

    private DeviceBuilder() {
        this.device = new Device();
    }

    public static DeviceBuilder builder() {
        return new DeviceBuilder();
    }

    public Device build() {
        return this.device;
    }

    public DeviceBuilder withUdid(String udid) {
        this.device.setUdid(udid);
        return this;
    }

    public DeviceBuilder withName(String name) {
        this.device.setName(name);
        return this;
    }

    public DeviceBuilder withState(String state) {
        this.device.setState(state);
        return this;
    }

    public DeviceBuilder withAvailable(boolean available) {
        this.device.setAvailable(available);
        return this;
    }

    public DeviceBuilder withOsVersion(String osVersion) {
        this.device.setOsVersion(osVersion);
        return this;
    }

    public DeviceBuilder withOs(String os) {
        this.device.setOs(os);
        return this;
    }

    public DeviceBuilder withDeviceType(String deviceType) {
        this.device.setDeviceType(deviceType);
        return this;
    }

    public DeviceBuilder withBrand(String brand) {
        this.device.setBrand(brand);
        return this;
    }

    public DeviceBuilder withApiLevel(String apiLevel) {
        this.device.setApiLevel(apiLevel);
        return this;
    }

    public DeviceBuilder withDevice(boolean device) {
        this.device.setDevice(device);
        return this;
    }

    public DeviceBuilder withDeviceModel(String deviceModel) {
        this.device.setDeviceModel(deviceModel);
        return this;
    }

    public DeviceBuilder withScreenSize(String screenSize) {
        this.device.setScreenSize(screenSize);
        return this;
    }

    public DeviceBuilder withDeviceManufacturer(String deviceManufacturer) {
        this.device.setDeviceManufacturer(deviceManufacturer);
        return this;
    }

    public DeviceBuilder withCloud(boolean cloud) {
        this.device.setCloud(cloud);
        return this;
    }
}
