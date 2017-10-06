package com.qxcmp.framework.mall.web;

import com.qxcmp.framework.mall.CommodityService;
import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.header.IconHeader;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping("/mall")
@RequiredArgsConstructor
public class MallPageController extends QXCMPController {

    private final CommodityService commodityService;

    private final MallPageHelper mallPageHelper;

    @GetMapping("/item/{id}.html")
    public ModelAndView commodityDetailsPage(@PathVariable String id, Device device) {

        return commodityService.findOne(id).map(commodity -> page().addComponent(device.isMobile() ? mallPageHelper.nextMobileCommodityDetails(commodity) : mallPageHelper.nextMobileCommodityDetails(commodity))
                .setTitle(String.format("%s_%s", commodity.getTitle(), siteService.getTitle()))
                .hideMobileBottomMenu()
                .build()).orElse(page(new Overview(new IconHeader("商品不存在", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/mall")).build());
    }
}
