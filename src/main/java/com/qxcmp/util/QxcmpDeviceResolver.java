package com.qxcmp.util;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户设备解析器
 * <p>
 * 用于解析用户发起请求的设备类型
 *
 * @author Aaric
 */
@Component
public class QxcmpDeviceResolver {

    private final DeviceResolver deviceResolver = new LiteDeviceResolver();

    public Device resolve(HttpServletRequest request) {
        return deviceResolver.resolveDevice(request);
    }
}
