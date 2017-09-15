package com.qxcmp.framework.web.view.elements.image;

import com.qxcmp.framework.web.QXCMPFrontendController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/image")
public class AbstractImageController extends QXCMPFrontendController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page().addComponent( new Avatar(siteService.getLogo())).build();
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page().addComponent( new Image(siteService.getLogo())).build();
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page().addComponent(new Images()).build();
    }
}
