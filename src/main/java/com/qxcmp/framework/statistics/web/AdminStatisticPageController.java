package com.qxcmp.framework.statistics.web;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.statistics.AccessHistoryPageResult;
import com.qxcmp.framework.statistics.AccessHistoryService;
import com.qxcmp.framework.web.QXCMPController;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.*;

/**
 * @author Aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/statistic")
@RequiredArgsConstructor
public class AdminStatisticPageController extends QXCMPController {

    private static final int MAX_BAIDU_LINK_PUSH_SIZE = 2000;

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

    @GetMapping("/access/history")
    public ModelAndView accessHistoryPage(Pageable pageable) {
        return page().addComponent(convertToTable(new PageRequest(pageable.getPageSize(), pageable.getPageSize(), Sort.Direction.DESC, "dateCreated"), accessHistoryService))
                .setBreadcrumb("控制台", "", "网站统计", "statistic", "访问记录")
                .setVerticalNavigation(NAVIGATION_ADMIN_STATISTIC, NAVIGATION_ADMIN_STATISTIC_ACCESS_HISTORY)
                .build();
    }

    @GetMapping("/baidu/link")
    public ModelAndView statisticBaiduLinkPage(final AdminStatisticBaiduLinkForm form) {

        form.setSite(siteService.getDomain());

        return page().addComponent(convertToForm(form))
                .setVerticalNavigation(NAVIGATION_ADMIN_STATISTIC, NAVIGATION_ADMIN_STATISTIC_BAIDU_LINK)
                .setBreadcrumb("控制台", "", "网站统计", "statistic", "百度链接提交")
                .build();
    }

    @PostMapping("/baidu/link")
    public ModelAndView statisticBaiduLinkPage(@Valid final AdminStatisticBaiduLinkForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return page().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form)))
                    .setVerticalNavigation(NAVIGATION_ADMIN_STATISTIC, NAVIGATION_ADMIN_STATISTIC_BAIDU_LINK)
                    .setBreadcrumb("控制台", "", "网站统计", "statistic", "百度链接提交")
                    .build();
        }

        return submitForm(form, context -> {

            Map<String, Object> result = Maps.newLinkedHashMap();
            context.put("result", result);

            try {
                List<String> links = Lists.newArrayList();

                links.addAll(form.getLinks());

                if (StringUtils.isNotBlank(form.getLinkPrefix())) {

                    String suffix = StringUtils.isBlank(form.getLinkSuffix()) ? "" : form.getLinkSuffix();

                    for (int i = form.getLinkItemStart(); i <= form.getLinkItemEnd(); i++) {
                        links.add(String.format("%s%d%s", form.getLinkPrefix(), i, suffix));
                    }
                }

                int successCount = 0;
                int remainCount = 0;
                int round = (int) Math.ceil(links.size() / MAX_BAIDU_LINK_PUSH_SIZE);
                for (int i = 0; i < round; i++) {

                    StringBuilder stringBuilder = new StringBuilder();

                    for (int j = i * MAX_BAIDU_LINK_PUSH_SIZE; j < i * MAX_BAIDU_LINK_PUSH_SIZE + MAX_BAIDU_LINK_PUSH_SIZE; j++) {
                        try {
                            stringBuilder.append(links.get(j)).append("\n");
                        } catch (Exception e) {
                            break;
                        }
                    }

                    HttpResponse response = HttpRequest.post(String.format("http://data.zz.baidu.com/urls?site=%s&token=%s", form.getSite(), form.getToken())).body(stringBuilder.toString()).send();

                    int code = response.statusCode();

                    Map<String, Object> json = new Gson().fromJson(response.body(), new TypeToken<Map<String, Object>>() {
                    }.getType());

                    if (code == HttpStatus.OK.value()) {
                        successCount += Double.valueOf(json.get("success").toString());
                        remainCount = Double.valueOf(json.get("remain").toString()).intValue();
                    }
                }

                result.put("推送成功条数", successCount);
                result.put("当天剩余次数", remainCount);

            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }

        }, (stringObjectMap, overview) -> overview.addComponent(convertToTable((Map<String, Object>) stringObjectMap.get("result"))));
    }
}
