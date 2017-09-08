package com.qxcmp.framework.web.controller.sample.elements;

import com.google.common.collect.Sets;
import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.divider.Divider;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.image.Avatar;
import com.qxcmp.framework.web.view.elements.list.List;
import com.qxcmp.framework.web.view.elements.list.item.*;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Set;

@Controller
@RequestMapping("/test/sample/list")
public class ListSamplePageController extends AbstractSamplePageController {

    private final static String PLACEHOLDER = "/assets/images/placeholder-square.png";

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent(new Grid().setColumnCount(ColumnCount.THREE)
                .addItem(new Row()
                        .addCol(new Col().addComponent(createStandardListSegment()))
                        .addCol(new Col().addComponent(createIconListSegment()))
                        .addCol(new Col().addComponent(createImageListSegment()))
                )));
    }

    private Component createImageListSegment() {
        return new Segment().addComponent(new ContentHeader("图片列表", Size.LARGE).setDividing())
                .addComponent(new List().setLink().addItems(createImageItems()))
                .addComponent(new Divider())
                .addComponent(new List().setLink().setHorizontal().addItems(createImageItems()))
                .addComponent(new Divider())
                .addComponent(new List().setLink().setSelection().addItems(createImageItems()))
                .addComponent(new Divider())
                .addComponent(new List().setLink().setDivided().setRelaxed().addItems(createImageItems()))
                .addComponent(new Divider())
                .addComponent(new List().setLink().setCelled().setRelaxed().addItems(createImageItems()))
                .addComponent(new Divider())
                .addComponent(new List().setLink().setAnimated().addItems(createImageItems()))
                ;
    }

    private Component createIconListSegment() {
        return new Segment().addComponent(new ContentHeader("图标列表", Size.LARGE).setDividing())
                .addComponent(new List().setLink().addItems(createIconItems()))
                .addComponent(new Divider())
                .addComponent(new List().setLink().setHorizontal().addItems(createIconItems()))
                .addComponent(new Divider())
                .addComponent(new List().setLink().setSelection().addItems(createIconItems()))
                .addComponent(new Divider())
                .addComponent(new List().setLink().setDivided().setRelaxed().addItems(createIconItems()))
                .addComponent(new Divider())
                .addComponent(new List().setLink().setCelled().setRelaxed().addItems(createIconItems()))
                .addComponent(new Divider())
                .addComponent(new List().setLink().setAnimated().addItems(createIconItems()))
                ;
    }

    private Component createStandardListSegment() {
        return new Segment().addComponent(new ContentHeader("文本列表", Size.LARGE).setDividing())
                .addComponent(new List().addItems(createListTextItems()))
                .addComponent(new Divider())
                .addComponent(new List().setHorizontal().addItems(createListTextItems()))
                .addComponent(new Divider())
                .addComponent(new List().setSelection().addItems(createListTextItems()))
                .addComponent(new Divider())
                .addComponent(new List().setDivided().setRelaxed().addItems(createListTextItems()))
                .addComponent(new Divider())
                .addComponent(new List().setCelled().setRelaxed().addItems(createListTextItems()))
                ;
    }

    private Collection<AbstractListItem> createImageItems() {
        Set<AbstractListItem> iconItems = Sets.newLinkedHashSet();
        for (int i = 0; i < 3; i++) {
            iconItems.add(new ImageHeaderItem(new Avatar(PLACEHOLDER), "标题", "项目描述").setUrl("/test/sample/list"));
        }
        return iconItems;
    }

    private Collection<AbstractListItem> createIconItems() {
        Set<AbstractListItem> iconItems = Sets.newLinkedHashSet();
        for (int i = 0; i < 3; i++) {
            iconItems.add(new IconHeaderItem(new Icon("users"), "标题", "项目描述").setUrl("/test/sample/list").addItem(new IconTextItem("user", "子项目")));
        }
        return iconItems;
    }

    private Collection<AbstractListItem> createListTextItems() {
        Set<AbstractListItem> textItems = Sets.newLinkedHashSet();
        for (int i = 0; i < 3; i++) {
            textItems.add(new TextItem("列表项目").setUrl("/test/sample/list")
                    .addItem(new TextItem("子项目").addItem(new TextItem("子项目")))
                    .addItem(new TextItem("子项目")));
        }
        return textItems;
    }

}
