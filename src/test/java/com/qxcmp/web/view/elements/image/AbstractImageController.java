package com.qxcmp.web.view.elements.image;

import com.qxcmp.web.QxcmpController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/image")
public class AbstractImageController extends QxcmpController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page().addComponent(new Avatar(siteService.getLogo())).build();
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page().addComponent(new Image(siteService.getLogo())).build();
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page().addComponent(new Images()).build();
    }
}
