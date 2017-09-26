package com.qxcmp.framework.core.web;

import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.container.TextContainer;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/profile")
@RequiredArgsConstructor
public class AdminProfilePageController extends QXCMPBackendController {

    @GetMapping("/info")
    public ModelAndView infoPage(final AdminProfileInfoForm form) {
        User user = currentUser().orElseThrow(RuntimeException::new);

        form.setPortrait(user.getPortrait());
        form.setName(user.getName());
        form.setNickname(user.getNickname());
        form.setPersonalizedSignature(user.getPersonalizedSignature());

        return page().addComponent(new TextContainer().addComponent(new Segment().addComponent(convertToForm(form))))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "个人中心", "", "基本资料")
                .build();
    }

    @PostMapping("/info")
    public ModelAndView infoPage(@Valid final AdminProfileInfoForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page().addComponent(new TextContainer().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form)))))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "个人中心", "", "基本资料")
                    .build();
        }

        return submitForm(form, context -> {
            try {
                User user = currentUser().orElseThrow(RuntimeException::new);
                userService.update(user.getId(), u -> {
                    u.setPortrait(form.getPortrait());
                    u.setName(form.getName());
                    u.setNickname(form.getNickname());
                    u.setPersonalizedSignature(form.getPersonalizedSignature());
                }).ifPresent(u -> refreshUser());
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        });
    }
}
