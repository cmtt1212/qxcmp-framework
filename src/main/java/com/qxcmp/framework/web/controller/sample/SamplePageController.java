package com.qxcmp.framework.web.controller.sample;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.containers.grid.CelledGrid;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.html.Anchor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample")
public class SamplePageController extends QXCMPController {

    @GetMapping("")
    public ModelAndView home() {
        return page(() -> new Container().addComponent(new CelledGrid()
                .addItem(new Row().addCol(new Col().addComponent(new Anchor("基本元素", "/test/sample/html"))))));
    }
}
