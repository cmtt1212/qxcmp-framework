package com.qxcmp.framework.core.web.profile;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.grid.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfilePageController extends QXCMPController {

    private final ProfilePageHelper profilePageHelper;

    @GetMapping("/info")
    public ModelAndView infoPage(final ProfileInfoForm form) {
        return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(profilePageHelper.nextProfileInfoComponent(form))))).build();
    }
}
