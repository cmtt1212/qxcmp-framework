package com.qxcmp.framework.web.controller;


import com.qxcmp.framework.message.SystemMessage;
import com.qxcmp.framework.message.SystemMessageService;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.form.AdminToolSystemmessageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import java.util.Date;
import java.util.Optional;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@RequestMapping(QXCMP_BACKEND_URL)
@Controller
@RequiredArgsConstructor
public class SystemMessageController extends QXCMPBackendController{

    private final SystemMessageService systemMessageService;
    @GetMapping(path = "/account/systemmessage")
    public ModelAndView getMessage(Pageable pageable){
        return builder().setTitle("系统消息")
                .setTableView(pageable, systemMessageService)
                .build();
    }

    @GetMapping(path = "/tool/systemmessage")
    public ModelAndView getInput(AdminToolSystemmessageForm form){

        return builder().setTitle("发送系统消息")
                .setFormView(form)
                .build();
    }

    @PostMapping(path = "/tool/systemmessage")
    public ModelAndView sendMessage(@Validated @ModelAttribute(FORM_OBJECT) final AdminToolSystemmessageForm form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return builder().setTitle("发送系统消息")
                    .setFormView(form)
                    .build();
        }
        try{
            systemMessageService.create(() -> {
                SystemMessage systemMessage = new SystemMessage();
                systemMessage.setUserId(currentUser().getId());
                systemMessage.setTitle(form.getTitle());
                systemMessage.setContent(form.getContent());
                systemMessage.setReaded(false);
                systemMessage.setDate(new Date());
                return systemMessage;
            });
            return builder()
                    .setResult("发送成功", "")
                    .setResultNavigation("返回", QXCMP_BACKEND_URL + "/tool.systemmessage")
                    .build();
        }catch (Exception e){
            return error(HttpStatus.BAD_GATEWAY, e.getMessage()).build();
        }
    }
}
