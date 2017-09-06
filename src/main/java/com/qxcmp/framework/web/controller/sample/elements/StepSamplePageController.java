package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Grid;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.elements.step.Step;
import com.qxcmp.framework.web.view.elements.step.Steps;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.ItemCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/step")
public class StepSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent(new Grid().setColumnCount(ColumnCount.ONE)
                .addItem(new Row()
                        .addCol(new Col().addComponent(createStandardStepSegment()))
                        .addCol(new Col().addComponent(createOrderedStepSegment()))
                        .addCol(new Col().addComponent(createVerticalStepSegment()))
                )));
    }

    private Component createVerticalStepSegment() {
        return new Segment().addComponent(new ContentHeader("垂直步骤", Size.LARGE).setDividing())
                .addComponent(new Steps().setSize(Size.MINI).setVertical()
                        .addStep(new Step("truck", "发货", "填写发货信息").setCompleted())
                        .addStep(new Step("payment", "支付", "填写支付信息").setActive())
                        .addStep(new Step("info", "确认订单", "").setDisabled())
                );
    }

    private Component createOrderedStepSegment() {
        return new Segment().addComponent(new ContentHeader("顺序步骤", Size.LARGE).setDividing())
                .addComponent(new Steps().setOrdered().setFluid().setItemCount(ItemCount.THREE)
                        .addStep(new Step("发货", "填写发货信息").setCompleted())
                        .addStep(new Step("支付", "填写支付信息").setActive())
                        .addStep(new Step("确认订单", "").setDisabled())
                );
    }

    private Component createStandardStepSegment() {
        return new Segment().addComponent(new ContentHeader("标准步骤", Size.LARGE).setDividing())
                .addComponent(new Steps().setFluid().setItemCount(ItemCount.THREE)
                        .addStep(new Step("truck", "发货", "填写发货信息").setCompleted().setLink())
                        .addStep(new Step("payment", "支付", "填写支付信息").setActive().setLink())
                        .addStep(new Step("info", "确认订单", "").setDisabled().setLink())
                );
    }

}
