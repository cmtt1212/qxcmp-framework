package com.qxcmp.framework.statistics.web;


import com.qxcmp.framework.statistics.AccessHistoryPageResult;
import com.qxcmp.framework.statistics.AccessHistoryService;
import com.qxcmp.framework.web.QXCMPController;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.NAVIGATION_ADMIN_STATISTIC;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.NAVIGATION_ADMIN_STATISTIC_PAGES;

/**
 * @author Aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/statistic")
@RequiredArgsConstructor
public class AdminStatisticPageController extends QXCMPController {

    private final AccessHistoryService accessHistoryService;

    @GetMapping("")
    public ModelAndView statisticPage() {
        return page()
                .setVerticalNavigation(NAVIGATION_ADMIN_STATISTIC, "")
                .setBreadcrumb("控制台", "", "网站统计")
                .build();
    }

    @GetMapping("/pages")
    public ModelAndView statisticPagesPage(@RequestParam(defaultValue = "1") int date, Pageable pageable) {
        List<AccessHistoryPageResult> results = accessHistoryService.findByDateCreatedAfter(DateTime.now().minusDays(date).toDate(), new PageRequest(0, pageable.getPageSize())).getContent();

        return page()
                .addComponent(convertToTable(stringObjectMap -> results.forEach(accessHistoryPageResult -> stringObjectMap.put(accessHistoryPageResult.getUrl(), accessHistoryPageResult.getNbr()))))
                .setVerticalNavigation(NAVIGATION_ADMIN_STATISTIC, NAVIGATION_ADMIN_STATISTIC_PAGES)
                .setBreadcrumb("控制台", "", "网站统计", "statistic", "页面统计")
                .build();
    }

}
