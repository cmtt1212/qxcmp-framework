package com.qxcmp.framework.core.web;

import com.jcabi.manifests.Manifests;
import com.qxcmp.framework.core.QxcmpConfiguration;
import com.qxcmp.framework.web.QxcmpController;
import com.qxcmp.framework.web.view.elements.container.TextContainer;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.list.List;
import com.qxcmp.framework.web.view.elements.list.item.TextItem;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.views.Overview;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.qxcmp.framework.core.QxcmpConfiguration.QXCMP;
import static com.qxcmp.framework.core.QxcmpConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL)
public class AdminPageController extends QxcmpController {

    @GetMapping("")
    public ModelAndView homePage() {
        return page().addComponent(new VerticallyDividedGrid().setTextContainer().setVerticallyPadded().setAlignment(Alignment.CENTER).setColumnCount(ColumnCount.ONE).addItem(new Col()
                .addComponent(new Overview(new PageHeader(HeaderType.H1, siteService.getTitle()).setSubTitle("欢迎登陆")))
        )).build();
    }

    @GetMapping("/about")
    public ModelAndView aboutPage() {
        return page()
                .addComponent(new TextContainer().addComponent(new Overview(new PageHeader(HeaderType.H1, QXCMP)).addComponent(convertToTable(stringObjectMap -> {
                    String appVersion = QxcmpConfiguration.class.getPackage().getImplementationVersion();
                    String appBuildDate = "development";
                    String appStartUpDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(applicationContext.getStartupDate()));

                    try {
                        appBuildDate = Manifests.read("Build-Date");
                    } catch (Exception ignored) {

                    }

                    if (StringUtils.isBlank(appVersion)) {
                        appVersion = "暂无版本信息";
                    }

                    stringObjectMap.put("平台版本", appVersion);
                    stringObjectMap.put("构建日期", appBuildDate);
                    stringObjectMap.put("启动日期", appStartUpDate);
                    stringObjectMap.put("软件版本", System.getProperty("java.version"));
                })).addLink("返回", QXCMP_BACKEND_URL)))
                .setBreadcrumb("控制台", "", "关于")
                .build();
    }

    @GetMapping("/tools")
    public ModelAndView toolsPage() {
        return page().addComponent(new TextContainer().addComponent(new Segment().setAlignment(Alignment.CENTER)
                .addComponent(new PageHeader(HeaderType.H1, "系统工具").setDividing())
                .addComponent(new List().setSelection()
                        .addItem(new TextItem("兑换码管理").setUrl(QXCMP_BACKEND_URL + "/redeem"))
                        .addItem(new TextItem("链接管理").setUrl(QXCMP_BACKEND_URL + "/link"))
                        .addItem(new TextItem("广告管理").setUrl(QXCMP_BACKEND_URL + "/advertisement"))
                        .addItem(new TextItem("蜘蛛管理").setUrl(QXCMP_BACKEND_URL + "/spider"))
                        .addItem(new TextItem("系统日志").setUrl(QXCMP_BACKEND_URL + "/audit"))
                        .addItem(new TextItem("站内信").setUrl(QXCMP_BACKEND_URL + "/inbox"))
                )))
                .setBreadcrumb("控制台", "", "系统工具")
                .build();
    }
}