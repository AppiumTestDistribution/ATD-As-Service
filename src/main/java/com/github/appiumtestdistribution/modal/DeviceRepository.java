package com.github.appiumtestdistribution.modal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findDeviceByUdid(String udid);
}