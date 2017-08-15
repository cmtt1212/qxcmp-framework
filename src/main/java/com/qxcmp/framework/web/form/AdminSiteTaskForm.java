package com.qxcmp.framework.web.form;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

@FormView(caption = "任务调度配置（需要重启）")
@Data
public class AdminSiteTaskForm {

    @FormViewField(label = "线程池大小", type = InputFiledType.NUMBER)
    private int threadPoolSize;

    @FormViewField(label = "最大线程池大小", type = InputFiledType.NUMBER)
    private int maxPoolSize;

    @FormViewField(label = "队列大小", type = InputFiledType.NUMBER)
    private int queueSize;
}
