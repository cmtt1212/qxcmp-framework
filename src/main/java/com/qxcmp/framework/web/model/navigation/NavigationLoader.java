package com.qxcmp.framework.web.model.navigation;

import com.qxcmp.framework.core.QXCMPConfigurator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Comparator;

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
        return Integer.MIN_VALUE + 5;
    }

    @Override
    public void config() {
        applicationContext.getBeansOfType(NavigationConfigurator.class).values().stream().sorted(Comparator.comparingInt(NavigationConfigurator::order)).forEach(navigationConfigurator -> navigationConfigurator.configureNavigation(navigationService));
    }
}
