package com.qxcmp.framework.web.controller;


import com.qxcmp.framework.message.SystemMessageService;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.form.SystemMessageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@RequestMapping(QXCMP_BACKEND_URL)
@Controller
@RequiredArgsConstructor
public class SystemMessageController extends QXCMPBackendController{

    private final SystemMessageService systemMessageService;
    @GetMapping(path = "/account/systemmessage")
    public ModelAndView set(Pageable pageable){
        return builder().setTitle("系统消息")
                .setTableView(pageable, systemMessageService)
                .build();
    }

    @GetMapping(path = "/tool/systemmessage")
    public ModelAndView set(SystemMessageForm form){

        return builder().setTitle("发送系统消息")
                .setFormView(form)
                .build();
    }
}
