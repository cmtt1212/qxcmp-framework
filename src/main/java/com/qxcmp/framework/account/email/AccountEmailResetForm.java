package com.qxcmp.framework.account.email;

import com.qxcmp.framework.account.AccountResetForm;
import com.qxcmp.framework.domain.Code;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

/**
 * 邮箱账户密码找回平台统一表单
 * <p>
 * 用户在使用邮箱找回密码的时候需要填写的信息
 * <p>
 * 找回流程如下： <ol> <li>填写用户邮箱</li> <li>检查邮箱格式以及是否存在</li> <li>生成重置密码链接{@link Code}，并发送到该邮箱</li>
 * <li>用户点击该重置密码链接，生成账户密码重置表单{@link AccountResetForm}</li> <li>提交密码重置表单以修改账户密码</li> </ol>
 *
 * @author aaric
 */
@FormView(caption = "找回密码", submitTitle = "发送重置链接", disableSubmitIcon = true, showDialog = false)
@Data
public class AccountEmailResetForm {

    /**
     * 用户账户所绑定的邮箱
     * <p>
     * 需要改邮箱已经存在，并且符合邮箱格式
     */
    @FormViewField(type = InputFiledType.TEXT, label = "邮箱", placeholder = "输入您账户所绑定的邮箱", autoFocus = true)
    @Email
    private String email;

    @FormViewField(label = "验证码", type = InputFiledType.CAPTCHA, placeholder = "请输入验证码")
    private String captcha;
}
