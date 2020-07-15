package com.github.appiumtestdistribution.service;

import com.github.appiumtestdistribution.helpers.Helpers;
import com.github.appiumtestdistribution.modal.XcodeResponse;
import lombok.SneakyThrows;

public class XcodeService {

    @SneakyThrows
    public XcodeResponse getXcodeDetails(){
        String commandOutput = Helpers.excuteProcess("xcodebuild -version");
        String[] results = commandOutput.split("\n");
        XcodeResponse response = new XcodeResponse();
        response.setXcodeVersion(results[0].replace("Xcode ",""));
        response.setBuildNumber(results[1].replace("Build version ",""));
        return response;
    }

}
