package com.qxcmp.framework.web.controller.sample;

import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.html.H4;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/icon")
public class IconSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container()
                .addComponent(createBasicIconSegment())
                .addComponent(createStateIconSegment())
        );
    }

    private Component createBasicIconSegment() {
        return new Segment().addComponent(new H4("基本图标"))
                .addComponent(new Icon("user", randomColor()))
                .addComponent(new Icon("download", randomColor()))
                .addComponent(new Icon("close", randomColor()))
                .addComponent(new Icon("send", randomColor()))
                .addComponent(new Icon("theme", randomColor()))
                .addComponent(new Icon("undo", randomColor()))
                .addComponent(new Icon("edit", randomColor()))
                .addComponent(new Icon("wait", randomColor()))
                .addComponent(new Icon("user circle", randomColor()))
                ;
    }

    private Component createStateIconSegment() {
        Icon user = new Icon("user", randomColor());
        user.setDisabled(true);
        Icon spinner = new Icon("spinner", randomColor());
        Icon circle = new Icon("circle", randomColor());
        Icon asterisk = new Icon("asterisk", randomColor());
        spinner.setLoading(true);
        circle.setLoading(true);
        asterisk.setLoading(true);
        return new Segment().addComponent(new H4("图标状态"))
                .addComponent(user)
                .addComponent(circle)
                .addComponent(asterisk)
                .addComponent(spinner);
    }
}
