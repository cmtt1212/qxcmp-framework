package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.account.AccountService;
import com.qxcmp.framework.domain.ImageService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.view.dictionary.DictionaryView;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.web.QXCMPBackendController2;
import com.qxcmp.framework.web.form.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 个人中心页面路由
 *
 * @author aairc
 */
@RequestMapping(QXCMP_BACKEND_URL + "/account")
@RequiredArgsConstructor
public class AccountModuleController extends QXCMPBackendController2 {

    private static final String EMAIL_BINDING_SESSION_ATTR = "EMAIL_BINDING_CAPTCHA";
    private static final String EMAIL_BINDING_CONTENT_SESSION_ATTR = "EMAIL_BINDING_CONTENT";

    private final ImageService imageService;

    private final AccountService accountService;

    @GetMapping
    public ModelAndView account() {
        return builder().setTitle("个人中心")
                .setResult("我的账户资料", "")
                .addDictionaryView(getUserInfo(currentUser()))
                .addNavigation(Navigation.Type.NORMAL, "个人中心")
                .build();
    }

    @GetMapping("/profile")
    public ModelAndView profileInfo(final AdminAccountProfileForm form) {

        User current = userService.currentUser();
        form.setUsername(current.getUsername());
        form.setNickname(current.getNickname());
        form.setPersonalizedSignature(current.getPersonalizedSignature());

        return builder().setTitle("我的资料")
                .setFormView(form)
                .addNavigation("我的资料", Navigation.Type.NORMAL, "个人中心")
                .build();
    }

    @PostMapping("/profile")
    public ModelAndView profileInfoEdit(@Valid @ModelAttribute(FORM_OBJECT) AdminAccountProfileForm form, BindingResult bindingResult) {

        if (!currentUser().getUsername().equals(form.getUsername()) && userService.findById(form.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "Username.exist");
        }

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        try {
            /*
            * 保存用户头像
            * */
            if (StringUtils.isNotEmpty(form.getPortrait().getOriginalFilename())) {
                imageService.store(form.getPortrait().getInputStream(), FilenameUtils.getExtension(form.getPortrait().getOriginalFilename()), 64, 64).ifPresent(image -> {
                    userService.update(currentUser().getId(), user -> {
                        user.setUsername(form.getUsername());
                        user.setNickname(form.getNickname());
                        user.setPersonalizedSignature(form.getPersonalizedSignature());
                        user.setPortrait(String.format("/api/image/%s.%s", image.getId(), image.getType()));
                    });
                });
            } else {
                userService.update(currentUser().getId(), user -> {
                    user.setUsername(form.getUsername());
                    user.setNickname(form.getNickname());
                    user.setPersonalizedSignature(form.getPersonalizedSignature());
                });
            }

            refreshUser();

            return builder().setResult("更新我的资料成功", "").setResultNavigation("返回", QXCMP_BACKEND_URL + "/account/profile").build();
        } catch (IOException e) {
            return builder().setResult("更新我的资料失败", e.getMessage()).setResultNavigation("返回", QXCMP_BACKEND_URL + "/account/profile").build();
        }
    }

    @GetMapping("/password")
    public ModelAndView passwordGet(final AdminAccountPasswordForm form) {
        form.setUsername(currentUser().getUsername());
        return builder().setTitle("密码修改")
                .setFormView(form)
                .addNavigation("密码修改", Navigation.Type.NORMAL, "个人中心")
                .build();
    }

    @PostMapping("/password")
    public ModelAndView passwordPost(@Valid @ModelAttribute("object") AdminAccountPasswordForm form, BindingResult bindingResult) {

        if (!new BCryptPasswordEncoder().matches(form.getOldPassword(), currentUser().getPassword())) {
            bindingResult.rejectValue("oldPassword", "BadCredential");
        }

        if (!StringUtils.equals(form.getNewPassword(), form.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "PasswordConfirm");
        }

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        return action("修改密码", context -> userService.update(currentUser().getId(), user ->
                user.setPassword(new BCryptPasswordEncoder().encode(form.getNewPassword())))).build();
    }

    @GetMapping("/email")
    public ModelAndView emailBindGet(final AdminAccountEmailForm form) {
        return builder().setTitle("邮箱绑定")
                .setFormView(form)
                .addNavigation("邮箱绑定", Navigation.Type.NORMAL, "个人中心")
                .build();
    }

    @PostMapping("/email")
    public ModelAndView emailBindPost(@Valid @ModelAttribute(FORM_OBJECT) AdminAccountEmailForm form, BindingResult bindingResult) {

        userService.findByEmail(form.getEmail()).ifPresent(user -> bindingResult.rejectValue("email", "Account.bind.emailExist"));

        validateCaptcha(form.getCaptcha(), bindingResult);

        if (bindingResult.hasErrors()) {
            return builder().setTitle("邮箱绑定")
                    .setFormView(form)
                    .addNavigation("邮箱绑定", Navigation.Type.NORMAL, "个人中心")
                    .build();
        }

        try {
            String code = RandomStringUtils.randomAlphanumeric(8);
            accountService.sendBindEmail(form.getEmail(), code);

            request.getSession().setAttribute(EMAIL_BINDING_SESSION_ATTR, code);
            request.getSession().setAttribute(EMAIL_BINDING_CONTENT_SESSION_ATTR, form.getEmail());

            return builder().setTitle("邮箱绑定")
                    .setResult("绑定验证码已经发送到邮箱", "请查看邮件里面的验证码，输入验证码以绑定邮箱")
                    .setFormView(new AdminAccountEmailBindForm())
                    .addNavigation("邮箱绑定", Navigation.Type.NORMAL, "个人中心")
                    .build();
        } catch (Exception e) {
            return builder().setTitle("邮箱绑定")
                    .setResult("绑定验证码发送失败", e.getMessage())
                    .setResultNavigation("重新发送", QXCMP_BACKEND_URL + "/account/email")
                    .addNavigation("邮箱绑定", Navigation.Type.NORMAL, "个人中心")
                    .build();
        }
    }

    @PostMapping("/email/bind")
    public ModelAndView emailBindPost(@Valid @ModelAttribute(FORM_OBJECT) AdminAccountEmailBindForm form, BindingResult bindingResult) {

        if (Objects.isNull(request.getSession().getAttribute(EMAIL_BINDING_SESSION_ATTR)) || !StringUtils.equals(form.getCaptcha(), (String) request.getSession().getAttribute(EMAIL_BINDING_SESSION_ATTR)) || bindingResult.hasErrors()) {
            return builder().setTitle("邮箱绑定")
                    .setResult("绑定验证不正确或者已过期，", "请查看邮件里面的验证码，输入验证码以绑定邮箱")
                    .setFormView(new AdminAccountEmailBindForm())
                    .addNavigation("邮箱绑定", Navigation.Type.NORMAL, "个人中心")
                    .build();
        }

        try {
            String email = (String) request.getSession().getAttribute(EMAIL_BINDING_CONTENT_SESSION_ATTR);
            userService.update(currentUser().getId(), user -> user.setEmail(email));
            refreshUser();
            return builder().setTitle("邮箱绑定")
                    .setResult("邮箱绑定成功", "您已经成功绑定邮箱:" + email)
                    .setResultNavigation("查看我的资料", QXCMP_BACKEND_URL + "/account")
                    .build();
        } catch (Exception e) {
            return builder().setTitle("邮箱绑定")
                    .setResult("邮箱绑定失败", e.getMessage())
                    .setResultNavigation("重新绑定", QXCMP_BACKEND_URL + "/account/email")
                    .build();
        }

    }

    @GetMapping("/phone")
    public ModelAndView phoneBindGet(final AdminAccountPhoneForm form) {
        return builder().setTitle("手机绑定")
                .setFormView(form)
                .addNavigation("手机绑定", Navigation.Type.NORMAL, "个人中心")
                .build();
    }

    @PostMapping("/phone")
    public ModelAndView phoneBindPost(@Valid @ModelAttribute(FORM_OBJECT) final AdminAccountPhoneForm form, BindingResult bindingResult) {

        userService.findByPhone(form.getPhone()).ifPresent(user -> bindingResult.rejectValue("phone", "Account.bind.phoneExist"));

        validateCaptcha(form.getCaptcha(), bindingResult);

        if (bindingResult.hasErrors()) {
            return builder().setTitle("手机绑定")
                    .setFormView(form)
                    .addNavigation("手机绑定", Navigation.Type.NORMAL, "个人中心")
                    .build();
        }

        try {
            userService.update(currentUser().getId(), user -> user.setPhone(form.getPhone()));
            refreshUser();
            return builder().setTitle("手机绑定")
                    .setResult("手机绑定成功", "您已经成功绑定手机号:" + form.getPhone())
                    .setResultNavigation("查看我的资料", QXCMP_BACKEND_URL + "/account")
                    .build();
        } catch (Exception e) {
            return builder().setTitle("手机绑定")
                    .setResult("手机绑定失败", e.getMessage())
                    .setResultNavigation("重新绑定", QXCMP_BACKEND_URL + "/account/phone")
                    .build();
        }
    }

    private DictionaryView getUserInfo(User user) {

        DictionaryView.DictionaryViewBuilder dictionaryViewBuilder = DictionaryView.builder();

        if (StringUtils.isNotBlank(user.getUsername())) {
            dictionaryViewBuilder.dictionary("用户名", user.getUsername());
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            dictionaryViewBuilder.dictionary("绑定邮箱", user.getEmail());
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            dictionaryViewBuilder.dictionary("绑定手机", user.getPhone());
        }
        if (StringUtils.isNotBlank(user.getNickname())) {
            dictionaryViewBuilder.dictionary("昵称", user.getNickname());
        }
        if (StringUtils.isNotBlank(user.getSex())) {
            dictionaryViewBuilder.dictionary("性别", user.getSex());
        }
        if (StringUtils.isNotBlank(user.getLanguage())) {
            dictionaryViewBuilder.dictionary("语言", user.getLanguage());
        }
        if (StringUtils.isNotBlank(user.getCity())) {
            dictionaryViewBuilder.dictionary("城市", user.getCity());
        }
        if (StringUtils.isNotBlank(user.getProvince())) {
            dictionaryViewBuilder.dictionary("省份", user.getProvince());
        }
        if (StringUtils.isNotBlank(user.getCountry())) {
            dictionaryViewBuilder.dictionary("国家", user.getCountry());
        }
        if (Objects.nonNull(user.getBirthday())) {
            dictionaryViewBuilder.dictionary("生日", new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthday()));
        }
        if (StringUtils.isNotBlank(user.getPersonalizedSignature())) {
            dictionaryViewBuilder.dictionary("个性签名", user.getPersonalizedSignature());
        }

        return dictionaryViewBuilder.build();
    }
}