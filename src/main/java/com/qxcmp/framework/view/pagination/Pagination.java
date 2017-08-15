package com.qxcmp.framework.view.pagination;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页信息对象
 *
 * @author aaric
 */
@Data
public class Pagination {

    /* 当前页面 */
    private int current;

    /* 前一页URL */
    private String previous;

    /* 下一页URL */
    private String next;

    /* 第一页URL */
    private String first;

    /* 最后一页URL */
    private String last;

    /* 具体分页项目集合 */
    private List<PaginationItem> paginationItems = new ArrayList<>();
}
