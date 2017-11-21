package com.qxcmp.framework.statistics;

import com.qxcmp.framework.web.filter.QXCMPRequestEvent;
import com.qxcmp.framework.web.support.QXCMPDeviceResolver;
import com.qxcmp.framework.web.support.QXCMPIpAddressResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccessAddressListener {

    private final AccessAddressService accessAddressService;
    private final QXCMPIpAddressResolver ipAddressResolver;
    private final QXCMPDeviceResolver deviceResolver;

    @EventListener
    public void onRequest(QXCMPRequestEvent event) {
        try {
            HttpServletRequest request = event.getRequest();

            String ipAddress = ipAddressResolver.resolve(request);
            Device device = deviceResolver.resolve(request);

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
