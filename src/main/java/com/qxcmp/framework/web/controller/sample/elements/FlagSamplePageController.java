package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.Flag;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/flag")
public class FlagSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent(new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.SIXTEEN).setAlignment(Alignment.CENTER)
                .addItem(new Col().addComponent(new Flag("ad")))
                .addItem(new Col().addComponent(new Flag("ba")))
                .addItem(new Col().addComponent(new Flag("cn")))
                .addItem(new Col().addComponent(new Flag("de")))
                .addItem(new Col().addComponent(new Flag("eg")))
                .addItem(new Col().addComponent(new Flag("fi")))
                .addItem(new Col().addComponent(new Flag("ga")))
                .addItem(new Col().addComponent(new Flag("hk")))
                .addItem(new Col().addComponent(new Flag("id")))
                .addItem(new Col().addComponent(new Flag("jm")))
                .addItem(new Col().addComponent(new Flag("ke")))
                .addItem(new Col().addComponent(new Flag("la")))
                .addItem(new Col().addComponent(new Flag("na")))
                .addItem(new Col().addComponent(new Flag("om")))
                .addItem(new Col().addComponent(new Flag("pa")))
                .addItem(new Col().addComponent(new Flag("qa")))
                .addItem(new Col().addComponent(new Flag("re")))
                .addItem(new Col().addComponent(new Flag("sa")))
                .addItem(new Col().addComponent(new Flag("tc")))
                .addItem(new Col().addComponent(new Flag("ua")))
                .addItem(new Col().addComponent(new Flag("va")))
                .addItem(new Col().addComponent(new Flag("wf")))
                .addItem(new Col().addComponent(new Flag("ye")))
                .addItem(new Col().addComponent(new Flag("za")))
        ));
    }


}
