package com.github.appiumtestdistribution.error;

import static java.text.MessageFormat.format;

public class NoDeviceFoundException extends RuntimeException {
    private static final long serialVersionUID = 7326L;

    public NoDeviceFoundException() {
        super("No devices found on this machine.");
    }

    public NoDeviceFoundException(final String id) {
        super(format("No device with ID [{0}] found on this machine.", id));
    }
}