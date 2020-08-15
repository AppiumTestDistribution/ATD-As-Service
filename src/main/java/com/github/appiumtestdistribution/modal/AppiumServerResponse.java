package com.github.appiumtestdistribution.modal;

import lombok.Data;

@Data
public class AppiumServerResponse {
    private int port;
    private String connectionUrl;
}