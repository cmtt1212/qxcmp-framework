package com.qxcmp.web.view.elements.icon;

import com.qxcmp.web.QxcmpController;
import com.qxcmp.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/icons")
public class IconsController extends QxcmpController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page().addComponent(new Icons(new Icon("user"), new Icon("user")).setSize(Size.HUGE)).build();
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page().addComponent(new CornerIcons(new Icon("user"), new CornerIcon("user")).setSize(Size.HUGE)).build();
    }

}
