package com.github.appiumtestdistribution.error;

public class AppiumServiceNotFoundException extends RuntimeException{

    public AppiumServiceNotFoundException(){
        super("Appium Service not started");
    }

}
