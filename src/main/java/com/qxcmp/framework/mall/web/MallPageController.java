package com.qxcmp.framework.mall.web;

import com.qxcmp.framework.mall.Commodity;
import com.qxcmp.framework.mall.CommodityService;
import com.qxcmp.framework.mall.CommodityVersion;
import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.button.AbstractButton;
import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.elements.grid.AbstractGrid;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.IconHeader;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.html.P;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.support.Size;
import com.qxcmp.framework.web.view.support.Wide;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping("/mall")
@RequiredArgsConstructor
public class MallPageController extends QXCMPController {

    private final CommodityService commodityService;

    private final MallPageHelper mallPageHelper;

    @GetMapping("/item/{id}.html")
    public ModelAndView commodityDetailsPage(@PathVariable String id, Device device) {
        return commodityService.findOne(id)
                .filter(commodity -> !commodity.isDisabled())
                .map(commodity -> page().addComponent(device.isMobile() ? mallPageHelper.nextMobileCommodityDetails(commodity) : mallPageHelper.nextMobileCommodityDetails(commodity))
                        .setTitle(String.format("%s_%s", commodity.getTitle(), siteService.getTitle()))
                        .hideMobileBottomMenu()
                        .build()).orElse(page(new Overview(new IconHeader("商品已下架或被删除", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/mall")).build());
    }

    @GetMapping("/item/version-select")
    public ModelAndView commodityVersionSelectPage(@RequestParam String id) {
        return commodityService.findOne(id)
                .filter(commodity -> !commodity.isDisabled())
                .map(commodity -> {
                    List<Commodity> commodityList = commodityService.findByParentId(commodity.getParentId());

                    if (commodityList.size() <= 1) {
                        return page(viewHelper.nextWarningOverview("暂无关联商品", "").addLink("返回", String.format("/mall/item/%d.html", commodity.getId()))).build();
                    }

                    AbstractGrid grid = new Grid().setVerticallyPadded().setContainer();
                    grid.addItem(new Row().addCol(new Col(Wide.SIXTEEN)
                            .addComponent(new PageHeader(HeaderType.H2,
                                    new DecimalFormat("￥0.00").format((double) commodity.getSellPrice() / 100))
                                    .setSubTitle("商品编号：" + commodity.getId())
                                    .setImage(new Image(commodity.getCover()).setBordered().setRounded())
                            )
                    ));

                    List<CommodityVersion> commodityVersionList = commodityList.stream().flatMap(c -> c.getVersions().stream()).distinct().collect(Collectors.toList());

                    Map<String, List<String>> versions = commodityVersionList.stream().collect(Collectors.groupingBy(CommodityVersion::getName, Collectors.mapping(CommodityVersion::getValue, Collectors.toList())));

                    versions.forEach((name, values) -> {
                        Col col = new Col(Wide.SIXTEEN);

                        values.forEach(s -> {
                            AbstractButton button = new Button(s).setBasic().setSize(Size.MINI);

                            if (commodity.getVersions().stream().anyMatch(commodityVersion ->
                                    StringUtils.equals(commodityVersion.getName(), name) && StringUtils.equals(commodityVersion.getValue(), s))) {
                                button.setPrimary();
                            } else {
                                button.setSecondary();
                            }

                            col.addComponent(button);
                        });

                        grid.addItem(new Row().addCol(new Col(Wide.SIXTEEN).addComponent(new P(name))).addCol(col));
                    });


                    return page().addComponent(grid)
                            .hideMobileBottomMenu()
                            .build();

                }).orElse(page(viewHelper.nextWarningOverview("商品不存在", "").addLink("返回", "/mall")).build());
    }
}
