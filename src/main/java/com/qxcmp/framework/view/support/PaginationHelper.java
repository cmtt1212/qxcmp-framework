package com.qxcmp.framework.view.support;

import com.google.common.collect.Maps;
import com.qxcmp.framework.view.pagination.Pagination;
import com.qxcmp.framework.view.pagination.PaginationItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 分页工具
 *
 * @author aaric
 */
@Component
public class PaginationHelper {

    private static final int DEFAULT_PAGINATION_SIZE = 8;

    private static final String PAGE_FORMAT = "%s?page=%d&size=%d&sort=%s%s";

    /**
     * 获取一个分院和对象
     *
     * @param page    分页内容信息
     * @param baseUrl 分页项目起始Url
     *
     * @return 分页对象
     */
    public Pagination next(Page page, String baseUrl) {
        return next(page, baseUrl, queryPara -> {
        });
    }

    /**
     * 获取一个分页对象
     *
     * @param page      分页内容信息
     * @param baseUrl   分页项目起始Url
     * @param queryPara 分页项目Url查询参数
     *
     * @return 分页对象
     */
    public Pagination next(Page page, String baseUrl, Consumer<Map<String, String>> queryPara) {
        Map<String, String> map = Maps.newLinkedHashMap();

        queryPara.accept(map);

        Pagination pagination = new Pagination();

        int currentPage = page.getNumber();
        int pageSize = page.getSize();
        int totalPage = page.getTotalPages();
        String sort;

        if (page.getSort() != null) {
            sort = page.getSort().toString().replaceAll(": ", ",");
        } else {
            sort = "";
        }

        String queryString = "";

        if (!map.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            map.forEach((key, value) -> {
                if (StringUtils.isNotBlank(value)) {
                    stringBuilder.append("&").append(key).append("=").append(value);
                }
            });
            queryString = stringBuilder.toString();
        }

        pagination.setPrevious(String.format(PAGE_FORMAT, baseUrl, page.isFirst() ? 0 : page.previousPageable().getPageNumber(), pageSize, sort, queryString));
        pagination.setNext(String.format(PAGE_FORMAT, baseUrl, page.isLast() ? totalPage - 1 : page.nextPageable().getPageNumber(), pageSize, sort, queryString));
        pagination.setFirst(String.format(PAGE_FORMAT, baseUrl, 0, pageSize, sort, queryString));
        pagination.setLast(String.format(PAGE_FORMAT, baseUrl, totalPage - 1, pageSize, sort, queryString));

        int baseNumber = currentPage / DEFAULT_PAGINATION_SIZE;

        List<PaginationItem> paginationItems = new ArrayList<>();

        for (int i = 0; i < DEFAULT_PAGINATION_SIZE; i++) {
            int target = baseNumber * DEFAULT_PAGINATION_SIZE + i + 1;

            if (target > totalPage) {
                continue;
            }

            PaginationItem paginationItem = new PaginationItem();
            paginationItem.setTitle(String.valueOf(target));
            paginationItem.setLink(String.format(PAGE_FORMAT, baseUrl, target - 1, pageSize, sort, queryString));

            if (currentPage == target - 1) {
                paginationItem.setActive(true);
            }

            paginationItems.add(paginationItem);
        }

        pagination.setPaginationItems(paginationItems);
        pagination.setCurrent(currentPage + 1);

        return pagination;
    }
}
