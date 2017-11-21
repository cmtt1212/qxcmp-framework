package com.qxcmp.framework.web.filter;

import com.qxcmp.framework.statistics.AccessAddressService;
import com.qxcmp.framework.statistics.AccessAddressType;
import com.qxcmp.framework.web.support.QXCMPIpAddressResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 平台通用拦截器
 *
 * @author Aaric
 */
public class QXCMPFilter extends GenericFilterBean {

    private final ApplicationContext applicationContext;
    private final AccessAddressService accessAddressService;
    private final QXCMPIpAddressResolver ipAddressResolver;

    public QXCMPFilter(ApplicationContext applicationContext, AccessAddressService accessAddressService, QXCMPIpAddressResolver ipAddressResolver) {
        this.applicationContext = applicationContext;
        this.accessAddressService = accessAddressService;
        this.ipAddressResolver = ipAddressResolver;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        applicationContext.publishEvent(new QXCMPRequestEvent(request, response));

        if (accessAddressService.findOne(ipAddressResolver.resolve(request)).map(accessAddress -> Objects.equals(accessAddress.getType(), AccessAddressType.BLACK)).orElse(false)) {
            throw new RuntimeException("无法访问该站点");
        }
        
        filterChain.doFilter(request, response);
    }
}