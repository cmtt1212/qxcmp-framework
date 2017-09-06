package com.qxcmp.framework.web.controller.sample;

import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.label.BasicLabel;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample")
public class SamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView home() {
        return page(() -> new Container().addComponent(new VerticallyDividedGrid().setColumnCount(ColumnCount.ONE).setVerticallyPadded()
                .addItem(new Col().addComponent(new Segment().addComponent(new ContentHeader("元素组件", Size.LARGE).setDividing())
                        .addComponent(new BasicLabel("基本HTML元素").setUrl("/test/sample/html").setColor(randomColor()))
                        .addComponent(new BasicLabel("按钮").setUrl("/test/sample/button").setColor(randomColor()))
                        .addComponent(new BasicLabel("分隔符").setUrl("/test/sample/divider").setColor(randomColor()))
                        .addComponent(new BasicLabel("页眉").setUrl("/test/sample/header").setColor(randomColor()))
                        .addComponent(new BasicLabel("图标").setUrl("/test/sample/icon").setColor(randomColor()))
                        .addComponent(new BasicLabel("图片").setUrl("/test/sample/image").setColor(randomColor()))
                        .addComponent(new BasicLabel("标签").setUrl("/test/sample/label").setColor(randomColor()))
                        .addComponent(new BasicLabel("输入框").setUrl("/test/sample/input").setColor(randomColor()))
                        .addComponent(new BasicLabel("列表").setUrl("/test/sample/list").setColor(randomColor()))
                        .addComponent(new BasicLabel("加载器").setUrl("/test/sample/loader").setColor(randomColor()))
                        .addComponent(new BasicLabel("展示元素").setUrl("/test/sample/reveal").setColor(randomColor()))
                        .addComponent(new BasicLabel("步骤").setUrl("/test/sample/step").setColor(randomColor()))
                        .addComponent(new BasicLabel("扶手").setUrl("/test/sample/rail").setColor(randomColor()))
                        .addComponent(new BasicLabel("面包屑").setUrl("/test/sample/breadcrumb").setColor(randomColor()))
                        .addComponent(new BasicLabel("国旗").setUrl("/test/sample/flag").setColor(randomColor()))
                ))
                .addItem(new Col().addComponent(new Segment().addComponent(new ContentHeader("视图组件", Size.LARGE).setDividing())

                ))
                .addItem(new Col().addComponent(new Segment().addComponent(new ContentHeader("行为组件", Size.LARGE).setDividing())
                        .addComponent(new BasicLabel("下拉框").setUrl("/test/sample/dropdown").setColor(randomColor()))
                ))
        ));
    }
}
