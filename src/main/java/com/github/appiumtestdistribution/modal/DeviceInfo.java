package com.github.appiumtestdistribution.modal;

import lombok.Data;

@Data
public class DeviceInfo {
    private String  apiLevel;
    private String  brand;
    private String  deviceManufacturer;
    private String  deviceModel;
    private boolean isDevice;
    private String  name;
    private String  os;
    private String  osVersion;
    private String  screenSize;
    private String  udid;
}