package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.list.List;
import com.qxcmp.framework.web.view.elements.list.item.IconTextItem;
import com.qxcmp.framework.web.view.elements.list.item.TextItem;
import com.qxcmp.framework.web.view.elements.message.*;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.Attached;
import com.qxcmp.framework.web.view.support.ColumnCount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/message")
public class MessageSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent(new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.TWO)
                .addItem(new Col().addComponent(new Segment().addComponent(new Message("您有一条新的消息").setColor(randomColor()))))
                .addItem(new Col().addComponent(new Segment().addComponent(new Message("您有一条新的消息", "这里是消息内容").setColor(randomColor()))))
                .addItem(new Col().addComponent(new Segment().addComponent(new Message("您有一条新的消息", "这里是消息内容", createList()).setColor(randomColor()))))
                .addItem(new Col().addComponent(new Segment().addComponent(new Message("您有一条新的消息", createList()).setColor(randomColor()))))
                .addItem(new Col().addComponent(new Segment().addComponent(new Message("您有一条新的消息", "这里是消息内容").setIcon("inbox").setColor(randomColor()))))
                .addItem(new Col().addComponent(new Segment().addComponent(new Message("您有一条新的消息", "这里是消息内容").setIcon(new Icon("notched circle").setLoading()).setColor(randomColor()))))
                .addItem(new Col().addComponent(new Segment().addComponent(new Message("您有一条新的消息", "这里是消息内容").setIcon("inbox").setFloating().setColor(randomColor()))))
                .addItem(new Col().addComponent(new Segment().addComponent(new Message("您有一条新的消息, 这里是消息内容").setCompact().setColor(randomColor()))))
                .addItem(new Col().addComponent(new Segment().addComponent(new Message("您有一条新的消息", "这里是消息内容").setIcon("inbox").setAttached(Attached.ATTACHED).setColor(randomColor()))))
                .addItem(new Col().addComponent(new Segment().addComponent(new Message("您有一条新的消息", "这里是消息内容").setIcon("inbox").setAttached(Attached.BOTTOM).setColor(randomColor()))))
                .addItem(new Col().addComponent(new Segment().addComponent(new InfoMessage("您有一条新的消息", "这里是消息内容").setIcon("inbox"))))
                .addItem(new Col().addComponent(new Segment().addComponent(new SuccessMessage("您有一条新的消息", "这里是消息内容").setIcon("inbox"))))
                .addItem(new Col().addComponent(new Segment().addComponent(new WarningMessage("您有一条新的消息", "这里是消息内容").setIcon("inbox"))))
                .addItem(new Col().addComponent(new Segment().addComponent(new ErrorMessage("您有一条新的消息", "这里是消息内容").setIcon("inbox"))))
                .addItem(new Col().addComponent(new Segment().addComponent(new PositiveMessage("您有一条新的消息", "这里是消息内容").setIcon("inbox"))))
                .addItem(new Col().addComponent(new Segment().addComponent(new NegativeMessage("您有一条新的消息", "这里是消息内容").setIcon("inbox"))))
        ));
    }

    private List createList() {
        return new List()
                .addItem(new TextItem("消息文本"))
                .addItem(new IconTextItem("user", "消息文本"))
                ;
    }


}
