package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

import javax.validation.constraints.Max;

/**
 * 认证配置表单
 *
 * @author aaric
 */
@FormView(caption = "认证配置")
@Data
public class AdminSecurityAuthenticationForm {

    @Max(10)
    @FormViewField(type = InputFiledType.NUMBER, max = "10", label = "验证码触发阈值")
    private int captchaThreshold;

    @Max(10)
    @FormViewField(type = InputFiledType.NUMBER, max = "10", label = "验证码长度")
    private int captchaLength;

    @FormViewField(type = InputFiledType.SWITCH, label = "是否开启锁定账户功能")
    private boolean lockAccount;

    @Max(10)
    @FormViewField(type = InputFiledType.NUMBER, max = "10", label = "登录失败锁定账户次数阈值")
    private int lockThreshold;

    @FormViewField(type = InputFiledType.NUMBER, label = "账户锁定时间（分钟）")
    private int unlockDuration;

    @FormViewField(type = InputFiledType.SWITCH, label = "是否开启账户过期功能")
    private boolean expireAccount;

    @FormViewField(type = InputFiledType.NUMBER, label = "账户过期时间（天）")
    private int expireAccountDuration;

    @FormViewField(type = InputFiledType.SWITCH, label = "是否开启密码过期功能")
    private boolean expireCredential;

    @FormViewField(type = InputFiledType.NUMBER, label = "密码过期时间（天）")
    private int expireCredentialDuration;

    @FormViewField(type = InputFiledType.SWITCH, label = "是否开启唯一密码功能，即密码不能有重复")
    private boolean uniqueCredential;
}
