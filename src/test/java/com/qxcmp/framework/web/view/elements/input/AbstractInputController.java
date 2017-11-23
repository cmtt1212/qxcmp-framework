package com.qxcmp.framework.web.view.elements.input;

import com.qxcmp.framework.web.QxcmpController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/input")
public class AbstractInputController extends QxcmpController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page().addComponent(new Input("text")).build();
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page().addComponent(new IconInput("user", "text")).build();
    }

}
