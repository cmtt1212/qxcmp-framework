package com.qxcmp.framework.statistics;

import com.qxcmp.framework.user.User;
import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.web.filter.QXCMPRequestEvent;
import com.qxcmp.framework.web.support.QXCMPIpAddressResolver;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AccessHistoryListener {

    private final UserService userService;

    private final AccessHistoryService accessHistoryService;

    private final QXCMPIpAddressResolver ipAddressResolver;

    @EventListener
    public void onRequest(QXCMPRequestEvent event) {
        try {
            HttpServletRequest request = event.getRequest();

            String requestURI = request.getRequestURI();

            if (isAccessRequest(requestURI)) {
                AccessHistory accessHistory = accessHistoryService.next();
                accessHistory.setDateCreated(new Date());
                accessHistory.setIp(ipAddressResolver.resolve(request));
                accessHistory.setUrl(request.getRequestURL().toString());

                User user = userService.currentUser();

                if (Objects.nonNull(user)) {
                    accessHistory.setUserId(user.getId());
                }

                accessHistoryService.create(() -> accessHistory);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isAccessRequest(String requestURI) {
        return !StringUtils.startsWith(requestURI, "/api") && !StringUtils.startsWith(requestURI, "/admin");
    }
}
