package com.qxcmp.framework.view.nav;

import com.qxcmp.framework.core.QXCMPConfigurator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 平台后端侧边导航栏加载
 *
 * @author aaric
 */
@Component
@RequiredArgsConstructor
public class NavigationLoader implements QXCMPConfigurator {

    private final ApplicationContext applicationContext;

    private final NavigationService navigationService;

    @Override
    public int order() {
        return 3;
    }

    @Override
    public void config() {
        applicationContext.getBeansOfType(NavigationConfigurator.class).forEach((s, moduleSidebarConfig) -> moduleSidebarConfig.configureNavigation(navigationService));
    }
}
