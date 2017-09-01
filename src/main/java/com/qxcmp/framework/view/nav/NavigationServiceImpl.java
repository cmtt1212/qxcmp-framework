package com.qxcmp.framework.view.nav;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.view.component.AnchorTarget;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NavigationServiceImpl implements NavigationService {

    private Table<Navigation.Type, String, Navigation> navigationTable = HashBasedTable.create();

    @Override
    public Navigation get(Navigation.Type type, String name, int order) {
        if (navigationTable.contains(type, name)) {
            return navigationTable.get(type, name);
        } else {
            Navigation navigation = new Navigation(name, type, order, Lists.newArrayList());
            navigationTable.put(type, name, navigation);
            return navigation;
        }
    }

    @Override
    public List<Navigation> findByType(Navigation.Type type) {
        List<Navigation> navigationList = Lists.newArrayList();
        navigationTable.row(type).forEach((s, navigation) -> navigationList.add(navigation));
        navigationList.sort(Comparator.comparingInt(Navigation::getOrder));
        return navigationList;
    }

    @Override
    public List<Navigation> filterByUser(List<Navigation> navigationList, User user) {
        Set<String> userPrivileges = Sets.newHashSet();

        if (Objects.nonNull(user)) {
            user.getAuthorities().forEach(auth -> userPrivileges.add(auth.getAuthority().replaceAll("ROLE_", "")));
        }

        List<Navigation> distNavigation = Lists.newArrayList();

        navigationList.forEach(navigation -> {
            Navigation tmpNavigation = new Navigation(navigation.getName(), navigation.getType(), navigation.getOrder(), Lists.newArrayList());
            navigation.getItems().forEach(navigationItem -> {
                if (navigationItem.getPrivileges().isEmpty() || navigationItem.getPrivileges().stream().allMatch(userPrivileges::contains)) {
                    tmpNavigation.getItems().add(navigationItem);
                }
            });

            if (!tmpNavigation.getItems().isEmpty()) {
                distNavigation.add(tmpNavigation);
            }
        });

        return distNavigation;
    }

    @Override
    public void add(Navigation navigation, String title, String image, String icon, String link, AnchorTarget target, int order, String... privileges) {
        List<NavigationItem> items = navigation.getItems();

        if (items.stream().anyMatch(navigationItem -> StringUtils.equals(title, navigationItem.getTitle()))) {
            return;
        }

        items.add(new NavigationItem(title, image, icon, link, target.toString(), order, Sets.newHashSet(Arrays.asList(privileges))));

        items.sort(Comparator.comparingInt(NavigationItem::getOrder));
    }
}
