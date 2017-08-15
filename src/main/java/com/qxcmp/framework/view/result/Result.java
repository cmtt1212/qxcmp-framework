package com.qxcmp.framework.view.result;

import lombok.Builder;
import lombok.Data;

/**
 * 结果视图组件
 * <p>
 * 该组件用来反馈用户操作
 *
 * @author aaric aaric
 */
@Data
@Builder
public class Result {

    /**
     * 标题
     */
    private String title;

    /**
     * 子标题
     */
    private String subTitle;

    /**
     * 详细描述
     */
    private String description;
}
