package com.qxcmp.framework.message.web;

import com.qxcmp.framework.web.view.annotation.form.DateTimeField;
import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.TextAreaField;
import com.qxcmp.framework.web.view.annotation.form.TextInputField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Aaric
 */
@Form("新建网站通知")
@Data
public class AdminMessageSiteNotificationNewForm {

    @TextInputField(value = "通知类型", required = true, autoFocus = true)
    private String type;

    @DateTimeField(value = "生效时间", disableMinute = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date dateStart;

    @DateTimeField(value = "结束时间", disableMinute = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date dateEnd;

    @TextAreaField(value = "通知内容", maxLength = 255)
    private String content;
}
