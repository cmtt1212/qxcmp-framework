package com.qxcmp.framework.view.list;

import lombok.Builder;
import lombok.Data;

/**
 * 列表视图项目
 * <p>
 * 用于渲染列表视图项目
 * <p>
 * 支持使用构建器模式
 *
 * @author aaric
 */
@Data
@Builder
public class ListViewItem {

    /**
     * 图片链接
     */
    private String image;

    /**
     * 标题
     */
    private String title;

    /**
     * 子标题
     */
    private String subTitle;

    /**
     * 描述
     */
    private String description;

    /**
     * 额外信息
     */
    private String extraContent;

    /**
     * 超链接
     */
    private String link;

}
