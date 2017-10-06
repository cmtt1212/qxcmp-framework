package com.qxcmp.framework.web.support;

import com.qxcmp.framework.web.page.AbstractPage;
import com.qxcmp.framework.web.page.BackendPage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_ACCOUNT_URL;
import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 平台页面解析器
 * <p>
 * 负责根据请求把页面解析为对应的页面实体
 * <p>
 * <ol> <li>后台页面</li> <li>账户注册页面</li> <li>前端大屏页面</li> <li>前端平板页面</li> <li>前端移动页面</li> </ol>
 *
 * @author Aaric
 */
@Component
@RequiredArgsConstructor
public class QXCMPPageResolver {

    private final ApplicationContext applicationContext;

    private final QXCMPDeviceResolver deviceResolver;

    /**
     * 把请求解析为一个页面
     *
     * @param request 请求
     *
     * @return 解析后的页面
     */
    public AbstractPage resolve(HttpServletRequest request, HttpServletResponse response) {

        String requestURI = request.getRequestURI();

        if (StringUtils.startsWith(requestURI, QXCMP_BACKEND_URL)) {
            return applicationContext.getBean(BackendPage.class, request, response);
        } else if (StringUtils.startsWith(requestURI, QXCMP_ACCOUNT_URL)) {

        } else {
            Device device = deviceResolver.resolve(request);

            if (device.isMobile()) {

            } else if (device.isTablet()) {

            } else {

            }

        }
        return null;
    }
}
