package com.qxcmp.framework.web.view.elements.label;

import com.qxcmp.framework.web.QXCMPController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/label")
public class AbstractLabelController extends QXCMPController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page(() -> new Label("text"));
    }
}
