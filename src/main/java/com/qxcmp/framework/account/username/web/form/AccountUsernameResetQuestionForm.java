package com.qxcmp.framework.account.username.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 密保问题平台统一表单
 * <p>
 * 用户使用密码找回密码的时候需要填写的信息
 *
 * @author aaric
 * @see AccountUsernameResetForm
 */
@FormView(caption = "我的密保问题", submitTitle = "找回我的密码", disableSubmitIcon = true, showDialog = false)
@Data
public class AccountUsernameResetQuestionForm {

    @FormViewField(type = InputFiledType.HIDDEN)
    private String userId;

    /**
     * 密保问题一
     * <p>
     * 标签需要动态从用户对应的密保问题中设置
     */
    @FormViewField(type = InputFiledType.LABEL)
    private String question1;

    @FormViewField(type = InputFiledType.TEXT, autoFocus = true)
    private String answer1;

    /**
     * 密保问题二
     * <p>
     * 标签需要动态从用户对应的密保问题中设置
     */
    @FormViewField(type = InputFiledType.LABEL)
    private String question2;

    @FormViewField(type = InputFiledType.TEXT, autoFocus = true)
    private String answer2;

    /**
     * 密保问题三
     * <p>
     * 标签需要动态从用户对应的密保问题中设置
     */
    @FormViewField(type = InputFiledType.LABEL)
    private String question3;

    @FormViewField(type = InputFiledType.TEXT, autoFocus = true)
    private String answer3;

}
