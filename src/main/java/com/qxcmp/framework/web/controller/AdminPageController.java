package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.containers.Container;
import com.qxcmp.framework.web.view.elements.Image;
import com.qxcmp.framework.web.view.elements.Menu;
import com.qxcmp.framework.web.view.elements.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL)
public class AdminPageController extends QXCMPController {

    @GetMapping("")
    public ModelAndView home() {
        return page(() -> {
            Container container = nextComponent(Container.class);
            Menu menu = nextComponent(Menu.class);
            MenuItem menuItem1 = nextComponent(MenuItem.class);
            Image image = nextComponent(Image.class);
            image.setSource(qxcmpConfiguration.getLogo());
            menuItem1.setImage(image);
            MenuItem menuItem2 = nextComponent(MenuItem.class);
            menuItem2.setText("首页");
            menuItem2.setUrl("/admin");
            MenuItem menuItem3 = nextComponent(MenuItem.class);
            menuItem3.setText("关于我们");
            menuItem3.setUrl("/admin");
            menu.getItems().add(menuItem1);
            menu.getItems().add(menuItem2);
            menu.getItems().add(menuItem3);
            container.getComponents().add(menu);
            return container;
        });
    }
}
