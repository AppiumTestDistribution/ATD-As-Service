package com.github.appiumtestdistribution.modal;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "device")
@Data
public class Device {
    private String  apiLevel;
    private String  brand;
    private String  deviceManufacturer;
    private String  deviceModel;
    private String  deviceType;
    private boolean isAvailable;
    private boolean isCloud;
    private boolean isDevice;
    private String  name;
    private String  os;
    private String  osVersion;
    private String  screenSize;
    private String  state;
    private String  udid;

    public Device(final com.github.device.Device fromDevice) {
        setApiLevel(fromDevice.getApiLevel());
        setAvailable(fromDevice.isAvailable());
        setBrand(fromDevice.getBrand());
        setCloud(fromDevice.isCloud());
        setDevice(fromDevice.isDevice());
        setDeviceManufacturer(fromDevice.getDeviceManufacturer());
        setDeviceModel(fromDevice.getDeviceModel());
        setDeviceType(fromDevice.getDeviceType());
        setDeviceType(fromDevice.getDeviceType());
        setName(fromDevice.getName());
        setOs(fromDevice.getOs());
        setOsVersion(fromDevice.getOsVersion());
        setScreenSize(fromDevice.getScreenSize());
        setState(fromDevice.getState());
        setUdid(fromDevice.getUdid());
    }
}