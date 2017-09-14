package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.form.LoginForm;
import com.qxcmp.framework.web.view.elements.button.AnimatedButton;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.divider.HorizontalDivider;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.html.Anchor;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 账户登录、注册、重置页面路由
 *
 * @author Aaric
 */
@Controller
public class AccountPageController extends QXCMPController {

    @GetMapping("/login")
    public ModelAndView loginPage() {

        LoginForm loginForm = new LoginForm();

        return page().addComponent(new Grid().setVerticallyPadded().setTextContainer().setColumnCount(ColumnCount.ONE)
                .addItem(new Col().addComponent(new Segment()
                        .addComponent(new PageHeader(HeaderType.H2, qxcmpConfiguration.getTitle()).setImage(new Image(qxcmpConfiguration.getLogo())).setDividing())
                        .addComponent(convertToForm(loginForm).setSubmitButton(new AnimatedButton().setVisibleText("登录").setHiddenIcon(new Icon("sign in")).setAnimatedType(AnimatedButton.AnimatedType.FADE).setSecondary()))
                        .addComponent(new HorizontalDivider("或"))
                        .addComponent(new Container().setAlignment(Alignment.CENTER).addComponent(new Anchor("注册新用户", "/account/logon")).addComponent(new Anchor("忘记密码?", "/account/reset")))
                ))).addObject(loginForm)
                .build();
    }

}
