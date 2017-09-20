package com.qxcmp.framework.core.web;

import com.jcabi.manifests.Manifests;
import com.qxcmp.framework.core.QXCMPConfiguration;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.modules.table.*;
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

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP;
import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL)
public class AdminPageController extends QXCMPBackendController {

    @GetMapping("")
    public ModelAndView homePage() {
        return page().addComponent(new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.ONE)
                .addItem(new Col().addComponent(new PageHeader(HeaderType.H2, siteService.getTitle())))
        ).build();
    }

    @GetMapping("/about")
    public ModelAndView aboutPage() {
        return page().addComponent(new VerticallyDividedGrid().setVerticallyPadded().setAlignment(Alignment.CENTER).setColumnCount(ColumnCount.ONE).addItem(new Col()
                .addComponent(new Overview(new PageHeader(HeaderType.H1, QXCMP)).addComponent(getAboutContent()).addLink("返回", QXCMP_BACKEND_URL))
        )).build();
    }

    private AbstractTable getAboutContent() {

        String appVersion = QXCMPConfiguration.class.getPackage().getImplementationVersion();
        String appBuildDate = "development";
        String appStartUpDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(applicationContext.getStartupDate()));

        try {
            appBuildDate = Manifests.read("Build-Date");
        } catch (Exception ignored) {

        }

        if (StringUtils.isBlank(appVersion)) {
            appVersion = "暂无版本信息";
        }

        return new Table().setCelled().setSelectable()
                .setBody((AbstractTableBody) new TableBody()
                        .addRow(new TableRow().addCell(new TableData("平台版本")).addCell(new TableData(appVersion)))
                        .addRow(new TableRow().addCell(new TableData("构建日期")).addCell(new TableData(appBuildDate)))
                        .addRow(new TableRow().addCell(new TableData("启动日期")).addCell(new TableData(appStartUpDate)))
                );
    }

}
