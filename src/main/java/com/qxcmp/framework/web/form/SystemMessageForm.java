package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

import java.util.Date;

@FormView(caption = "系统消息")
@Data
public class SystemMessageForm {

    @FormViewField(type = InputFiledType.HIDDEN)
    private Long id;
    @FormViewField(type = InputFiledType.HIDDEN)
    private String userId;
    @FormViewField(type = InputFiledType.TEXT,label = "标题", autoFocus = true)
    private String title;
    @FormViewField(type = InputFiledType.TEXT, label = "内容", autoFocus = true)
    private String content;
    @FormViewField(type = InputFiledType.HIDDEN)
    private Date date;
    @FormViewField(type = InputFiledType.HIDDEN)
    private Boolean is_read;

}
