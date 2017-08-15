package com.qxcmp.framework.view.pagination;

import lombok.Data;

/**
 * 分页项目
 *
 * @author aaric
 */
@Data
public class PaginationItem {

    /* 项目文本 */
    private String title;

    /* 项目对应超链接 */
    private String link;

    /* 项目是否为激活状态，即是否为当前页面 */
    private boolean isActive;

}
