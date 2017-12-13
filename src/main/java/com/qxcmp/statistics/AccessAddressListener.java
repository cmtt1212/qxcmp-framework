package com.qxcmp.statistics;

import com.qxcmp.util.IpAddressResolver;
import com.qxcmp.web.filter.QxcmpRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

/**
 * @author Aaric
 */
@Component
@RequiredArgsConstructor
public class AccessAddressListener {

    private final AccessAddressService accessAddressService;
    private final IpAddressResolver ipAddressResolver;
    private final DeviceResolver deviceResolver;

    @EventListener
    public void onRequest(QxcmpRequestEvent event) {
        try {
            HttpServletRequest request = event.getRequest();

            String ipAddress = ipAddressResolver.resolve(request);
            Device device = deviceResolver.resolveDevice(request);

            Optional<AccessAddress> addressOptional = accessAddressService.findOne(ipAddress);

            if (addressOptional.isPresent()) {
                accessAddressService.update(ipAddress, a -> {
                    a.setDate(new Date());
                    a.setComments(device.isMobile() ? "手机" : (device.isTablet() ? "平板" : "电脑"));
                });
            } else {
                accessAddressService.create(() -> {
                    AccessAddress accessAddress = accessAddressService.next();

                    accessAddress.setAddress(ipAddress);
                    accessAddress.setDate(new Date());
                    accessAddress.setComments(device.isMobile() ? "手机" : (device.isTablet() ? "平板" : "电脑"));

                    return accessAddress;
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
