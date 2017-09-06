package com.qxcmp.framework.web.controller.sample;

import com.qxcmp.framework.web.view.containers.grid.AbstractGrid;
import com.qxcmp.framework.web.view.containers.grid.CelledGrid;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.html.H2;
import com.qxcmp.framework.web.view.elements.label.BasicLabel;
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
                            .addComponent(new BasicLabel("基本元素").setUrl("/test/sample/html").setColor(randomColor()))
                            .addComponent(new BasicLabel("按钮").setUrl("/test/sample/button").setColor(randomColor()))
                            .addComponent(new BasicLabel("分隔符").setUrl("/test/sample/divider").setColor(randomColor()))
                            .addComponent(new BasicLabel("页眉").setUrl("/test/sample/header").setColor(randomColor()))
                            .addComponent(new BasicLabel("图标").setUrl("/test/sample/icon").setColor(randomColor()))
                            .addComponent(new BasicLabel("图片").setUrl("/test/sample/image").setColor(randomColor()))
                            .addComponent(new BasicLabel("标签").setUrl("/test/sample/label").setColor(randomColor()))
                            .addComponent(new BasicLabel("输入框").setUrl("/test/sample/input").setColor(randomColor()))
                    ))
                    .addItem(new Row().addCol(new Col(Wide.SIXTEEN).addComponent(new H2("集合元素"))).addCol(new Col(Wide.SIXTEEN)))
                    .addItem(new Row().addCol(new Col(Wide.SIXTEEN).addComponent(new H2("常用视图"))).addCol(new Col(Wide.SIXTEEN)))
                    .addItem(new Row().addCol(new Col(Wide.SIXTEEN).addComponent(new H2("带行为组件"))).addCol(new Col(Wide.SIXTEEN)
                            .addComponent(new BasicLabel("下拉框").setUrl("/test/sample/dropdown").setColor(randomColor()))
                    ));

            return new Container().addComponent(grid);
        });
    }
}
