package com.github.appiumtestdistribution.modal;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AppiumServerRequest {
    private int port;
    private String appiumJSPath;
}
