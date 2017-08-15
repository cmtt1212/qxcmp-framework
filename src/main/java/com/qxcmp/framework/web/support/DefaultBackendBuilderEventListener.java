package com.qxcmp.framework.web.support;

import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.view.nav.NavigationService;
import com.qxcmp.framework.view.support.BackendBuilderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 平台默认后端页面事件处理
 * <p>
 * 负责为页面增加侧边导航栏
 *
 * @author aaric
 */
@Component
@RequiredArgsConstructor
public class DefaultBackendBuilderEventListener {

    private final UserService userService;

    private final NavigationService navigationService;

    @EventListener
    public void onBuilderEvent(BackendBuilderEvent event) {
        event.getModelAndViewBuilder()
                .addObject("sidebar", navigationService.filterByUser(navigationService.findByType(Navigation.Type.SIDEBAR), userService.currentUser()))
                .addObject("actionBar", navigationService.filterByUser(navigationService.findByType(Navigation.Type.ACTION), userService.currentUser()))
                .addObject("user", userService.currentUser());
    }
}
