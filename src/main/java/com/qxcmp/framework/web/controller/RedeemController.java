package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.redeem.RedeemKey;
import com.qxcmp.framework.redeem.RedeemKeyEvent;
import com.qxcmp.framework.redeem.RedeemKeyService;
import com.qxcmp.framework.redeem.RedeemKeyStatus;
import com.qxcmp.framework.web.QXCMPFrontendController2;
import com.qxcmp.framework.web.form.RedeemForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

/**
 * 兑换码前端页面路由
 *
 * @author aaric
 */
@Controller
@RequestMapping("/redeem")
@RequiredArgsConstructor
public class RedeemController extends QXCMPFrontendController2 {

    private final RedeemKeyService redeemKeyService;

    /**
     * 获取使用兑换码页面
     * <p>
     * 该页面为前端表单页面
     *
     * @return 使用兑换码页面
     */
    @GetMapping
    public ModelAndView redeemGet(@RequestParam(defaultValue = "/redeem") String callback, RedeemForm form) {
        form.setCallbackUrl(callback);
        return builder().setFormView(form).build();
    }

    /**
     * 使用兑换码
     * <p>
     * 如果兑换码存在且没有过期则使用该兑换码，并发送使用兑换码事件
     *
     * @param form          兑换码使用表单
     * @param bindingResult 绑定错误
     *
     * @return 使用结果页面
     */
    @PostMapping
    public ModelAndView redeemPost(@Valid @ModelAttribute(FORM_OBJECT) RedeemForm form, BindingResult bindingResult) {

        Optional<RedeemKey> redeemKeyOptional = redeemKeyService.findOne(form.getId());

        if (!redeemKeyOptional.isPresent()) {
            bindingResult.rejectValue("id", "无效的兑换码", "无效的兑换码");
            return builder().setFormView(form).build();
        }

        RedeemKey redeemKey = redeemKeyOptional.get();

        if (redeemKey.getStatus().equals(RedeemKeyStatus.USED)) {
            bindingResult.rejectValue("id", "兑换码已被使用", "兑换码已被使用");
        }

        if (System.currentTimeMillis() - redeemKey.getDateExpired().getTime() > 0) {
            bindingResult.rejectValue("id", "兑换码已过期", "兑换码已过期");
            redeemKeyService.update(redeemKey.getId(), redeem -> redeem.setStatus(RedeemKeyStatus.EXPIRED));
        }

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        redeemKeyService.update(redeemKey.getId(), redeem -> {
            redeem.setUserId(currentUser().getId());
            redeem.setStatus(RedeemKeyStatus.USED);
        }).ifPresent(key -> applicationContext.publishEvent(new RedeemKeyEvent(currentUser(), key)));

        return builder().setResult("使用兑换码成功", "").setResultNavigation("返回", form.getCallbackUrl()).build();
    }
}
