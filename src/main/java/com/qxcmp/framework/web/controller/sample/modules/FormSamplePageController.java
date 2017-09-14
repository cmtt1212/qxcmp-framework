package com.qxcmp.framework.web.controller.sample.modules;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Page;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.message.InfoMessage;
import com.qxcmp.framework.web.view.elements.message.SuccessMessage;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.form.AbstractForm;
import com.qxcmp.framework.web.view.support.ColumnCount;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/test/sample/form")
public class FormSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        TestSampleForm testSampleForm = new TestSampleForm();
        return getPage(convertToForm(testSampleForm).setInfoMessage((InfoMessage) new InfoMessage("关于表单说明", "本表单展示了所有的表单组件").setCloseable())).addObject(testSampleForm).build();
    }

    @PostMapping("")
    public ModelAndView sample(@Valid TestSampleForm form, BindingResult bindingResult) throws InterruptedException {

        verifyCaptcha(form.getCaptcha(), bindingResult);

        TimeUnit.SECONDS.sleep(1);

        if (bindingResult.hasErrors()) {
            return getPage(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))).addObject(form).build();
        }

        return getPage(convertToForm(form).setSuccessMessage(new SuccessMessage("提交成功", "表单信息提交成功"))).addObject(form).build();
    }

    private Page getPage(AbstractForm form) {
        return page().addComponent(new VerticallyDividedGrid().setTextContainer().setVerticallyPadded().setColumnCount(ColumnCount.ONE).addItem(new Col().addComponent(new Segment()
                .addComponent(form)
        )));
    }
}
