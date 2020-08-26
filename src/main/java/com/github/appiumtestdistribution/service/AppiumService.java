package com.github.appiumtestdistribution.service;

import com.github.appiumtestdistribution.error.AppiumServiceNotFoundException;
import com.github.appiumtestdistribution.helpers.Helpers;
import com.github.appiumtestdistribution.modal.AppiumServerRequest;
import com.github.appiumtestdistribution.modal.AppiumServerResponse;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AppiumService {

    private final String SUCCESS_MSG = "Success";
    private final String ERROR_MSG = "Error";

    AppiumDriverLocalService appiumDriverLocalService;

    @SneakyThrows
    public AppiumServerResponse startAppiumServer(AppiumServerRequest request) {
        String ipAddress = Helpers.getHostMachineIpAddress();
        AppiumServiceBuilder builder =
                new AppiumServiceBuilder()
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                        .withIPAddress(ipAddress);

        if (StringUtils.isNotEmpty(request.getAppiumJSPath())) {
            builder.withAppiumJS(new File(request.getAppiumJSPath()));
        }
        if (request.getPort() > 0) {
            builder.usingPort(request.getPort());
        } else {
            builder.usingAnyFreePort();
        }
        appiumDriverLocalService = AppiumDriverLocalService.buildService(builder);
        appiumDriverLocalService.start();
        System.out.println(
                "**************************************************************************\n");
        System.out.println("Appium Server Started at......"
                + appiumDriverLocalService.getUrl().getPort());
        System.out.println(
                "**************************************************************************\n");
        AppiumServerResponse response = new AppiumServerResponse();
        response.setPort(appiumDriverLocalService.getUrl().getPort());
        response.setConnectionUrl(appiumDriverLocalService.getUrl().toString());
        return response;
    }

    @SneakyThrows
    public String stopAppiumServer(){
        if(appiumDriverLocalService == null){
            throw new AppiumServiceNotFoundException();
        }
        appiumDriverLocalService.stop();
        if (!appiumDriverLocalService.isRunning()) {
            appiumDriverLocalService = null;
            return SUCCESS_MSG;
        }
        return ERROR_MSG;
    }
}