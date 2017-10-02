package com.qxcmp.framework.mall.web;

import com.qxcmp.framework.mall.Commodity;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.components.mall.CommodityActionBar;
import com.qxcmp.framework.web.view.components.mall.CommodityPrice;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.divider.HorizontalDivider;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.image.Images;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Size;

/**
 * 商城前端页面组件生成帮助类
 *
 * @author Aaric
 */
@org.springframework.stereotype.Component
public class MallPageHelper {

    /**
     * 商品详情移动端页面组件
     *
     * @param commodity
     * @return
     */
    public Component nextMobileCommodityDetails(Commodity commodity) {

        final Images details = new Images();

        commodity.getDetails().forEach(s -> details.addImage(new Image(s).setFluid()));

        return new Grid()
                .addItem(new Row().addCol(new Col().addComponent(new Image(commodity.getCover()).setFluid().setBordered())))
                .addItem(new Row().addCol(new Col().addComponent(new Container()
                        .addComponent(new PageHeader(HeaderType.H4, commodity.getTitle()))
                        .addComponent(new ContentHeader(commodity.getSubTitle(), Size.TINY).setColor(Color.RED))
                        .addComponent(new CommodityPrice(commodity))
                ))).addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(new HorizontalDivider("商品信息")).addComponent(details))))
                .addItem(new Row().addCol(new Col().addComponent(new CommodityActionBar())));
    }
}
