package com.qxcmp.framework.view.nav;

import com.qxcmp.framework.view.component.AnchorTarget;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NavigationServiceTest {

    private NavigationService navigationService = new NavigationServiceImpl();

    @Test
    public void testGetNavigation() throws Exception {
        Navigation navigation = navigationService.get(Navigation.Type.NORMAL, "nav1", 0);
        assertTrue(Objects.nonNull(navigation));
        assertEquals("nav1", navigation.getName());
        assertEquals(0, navigation.getOrder());

        Navigation nav2 = navigationService.get(Navigation.Type.NORMAL, "nav1", 2);
        assertTrue(Objects.nonNull(nav2));
        assertEquals("nav1", nav2.getName());
        assertEquals(0, nav2.getOrder());
    }

    @Test
    public void testFindByType() throws Exception {
        navigationService.get(Navigation.Type.NORMAL, "nav1", 0);
        navigationService.get(Navigation.Type.NORMAL, "nav2", 1);
        navigationService.get(Navigation.Type.NORMAL, "nav3", 2);
        navigationService.get(Navigation.Type.SIDEBAR, "nav1", 0);
        navigationService.get(Navigation.Type.SIDEBAR, "nav2", 3);
        navigationService.get(Navigation.Type.SIDEBAR, "nav3", 1);
        navigationService.get(Navigation.Type.SIDEBAR, "nav4", 2);

        List<Navigation> navigationList = navigationService.findByType(Navigation.Type.SIDEBAR);

        assertEquals(4, navigationList.size());
        assertEquals("nav4", navigationList.get(2).getName());
        assertEquals(2, navigationList.get(2).getOrder());
    }

    @Test
    public void testAddItem() throws Exception {
        Navigation navigation = navigationService.get(Navigation.Type.SIDEBAR, "sidebar", 0);
        navigationService.add(navigation, "Settings", "", "", "/setting", AnchorTarget.SELF, 3);
        navigationService.add(navigation, "Settings", "", "", "/setting", AnchorTarget.SELF, 3);
        navigationService.add(navigation, "Profile", "", "", "/profile", AnchorTarget.SELF, 2);
        navigationService.add(navigation, "Profile", "", "", "/profile", AnchorTarget.SELF, 2);
        navigationService.add(navigation, "Message", "", "", "/message", AnchorTarget.SELF, 1);
        navigationService.add(navigation, "Message", "", "", "/message", AnchorTarget.SELF, 1);

        assertEquals(3, navigation.getItems().size());
        assertEquals("Message", navigation.getItems().get(0).getTitle());
        assertEquals("Profile", navigation.getItems().get(1).getTitle());
        assertEquals("Settings", navigation.getItems().get(2).getTitle());
    }
}