package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.CustomComponent;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Grid;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPageController extends QXCMPController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return page(() -> new Grid().addItem(new Row().addCol(new Col()).addCol(new Col().addComponent(createLoginForm()))));
    }

    private Segment createLoginForm() {
        Segment segment = new Segment();
        segment.getComponents().add(new CustomComponent("qxcmp/components/login") {
        });
        return segment;
    }

}
