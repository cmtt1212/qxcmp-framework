package com.qxcmp.framework.core.web.profile;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.grid.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfilePageController extends QXCMPController {

    private final ProfilePageHelper profilePageHelper;

    @GetMapping("/info")
    public ModelAndView infoPage(final ProfileInfoForm form) {
        return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(profilePageHelper.nextProfileInfoComponent(form))))).build();
    }


    @PostMapping("/info")
    public ModelAndView infoPage(@Valid final AdminProfileInfoForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(profilePageHelper.nextProfileInfoComponent(form, bindingResult))))).build();
        }

        return submitForm(form, context -> profilePageHelper.executeProfileInfoSubmit(form).ifPresent(user -> refreshUser()), (stringObjectMap, overview) -> overview.addLink("返回", "/profile/info"));
    }
}
