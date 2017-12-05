package com.qxcmp.web.support;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class QXCMPDeviceResolver {

    private final DeviceResolver deviceResolver = new LiteDeviceResolver();

    public Device resolve(HttpServletRequest request) {
        return deviceResolver.resolveDevice(request);
    }
}
