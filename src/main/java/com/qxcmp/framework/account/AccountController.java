package com.qxcmp.framework.account;

import com.qxcmp.framework.domain.Code;
import com.qxcmp.framework.domain.CodeService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.view.form.FormViewEntity;
import com.qxcmp.framework.view.list.ListViewItem;
import com.qxcmp.framework.web.QXCMPFrontendController2;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

import static com.qxcmp.framework.account.AccountService.ACCOUNT_PAGE;

/**
 * 账户模块页面路由 <li>账户注册页面的处理</li> <li>账户重置页面的处理</li> <li>账户激活页面的处理</li>
 *
 * @author aaric
 */
@RequestMapping("/account/")
@RequiredArgsConstructor
public class AccountController extends QXCMPFrontendController2 {

    private final AccountService accountService;

    private final CodeService codeService;


}
