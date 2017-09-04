package com.qxcmp.framework.web.controller.sample;

import com.qxcmp.framework.web.view.containers.grid.AbstractGrid;
import com.qxcmp.framework.web.view.containers.grid.CelledGrid;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.html.Anchor;
import com.qxcmp.framework.web.view.elements.html.H2;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.Wide;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample")
public class SamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView home() {
        return page(() -> {

            AbstractGrid grid = new CelledGrid();
            grid.setAlignment(Alignment.CENTER);
            grid
                    .addItem(new Row().addCol(new Col(Wide.SIXTEEN).addComponent(new H2("无行为元素"))).addCol(new Col(Wide.SIXTEEN)
                            .addComponent(new Anchor("基本元素", "/test/sample/html"))
                            .addComponent(new Anchor("图标", "/test/sample/icon"))
                    ))
                    .addItem(new Row().addCol(new Col(Wide.SIXTEEN).addComponent(new H2("集合元素"))).addCol(new Col(Wide.SIXTEEN)))
                    .addItem(new Row().addCol(new Col(Wide.SIXTEEN).addComponent(new H2("常用视图"))).addCol(new Col(Wide.SIXTEEN)))
                    .addItem(new Row().addCol(new Col(Wide.SIXTEEN).addComponent(new H2("带行为组件"))).addCol(new Col(Wide.SIXTEEN)));

            return new Container().addComponent(grid);
        });
    }
}
