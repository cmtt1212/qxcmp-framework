package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.button.AnimatedButton;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.form.Form;
import com.qxcmp.framework.web.view.modules.form.FormMethod;
import com.qxcmp.framework.web.view.modules.form.field.PasswordField;
import com.qxcmp.framework.web.view.modules.form.field.TextInputField;
import com.qxcmp.framework.web.view.support.ColumnCount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPageController extends QXCMPController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return page(() -> new Grid().setTextContainer().setColumnCount(ColumnCount.ONE)
                .addItem(new Col().addComponent(new Segment()
                        .addComponent(new PageHeader(HeaderType.H2, qxcmpConfiguration.getTitle()).setDividing())
                        .addComponent(createLoginForm()))));
    }

    private Component createLoginForm() {
        return new Form().setMethod(FormMethod.POST).setSubmitButton(new AnimatedButton().setVisibleText("登录").setHiddenIcon(new Icon("sign in")).setAnimatedType(AnimatedButton.AnimatedType.FADE).setSecondary())
                .addItem(new TextInputField("username", "", "用户名").setPlaceholder("用户名/邮箱/手机"))
                .addItem(new PasswordField("password", "", "登录密码").setMaxLength(20).setPlaceholder("你的登录密码"))
                ;
    }

}
