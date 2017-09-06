package com.qxcmp.framework.web.view.elements.input;

import com.qxcmp.framework.web.QXCMPController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/input")
public class AbstractInputController extends QXCMPController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page(() -> new Input("text"));
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page(() -> new IconInput("user", "text"));
    }

}
