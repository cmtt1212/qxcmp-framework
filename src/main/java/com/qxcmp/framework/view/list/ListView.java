package com.qxcmp.framework.view.list;

import com.qxcmp.framework.view.pagination.Pagination;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

/**
 * 列表视图
 * <p>
 * 用于渲染列表视图
 * <p>
 * 支持使用构建器模式
 *
 * @author aaric
 */
@Data
@Builder
public class ListView {

    /**
     * 列表视图项目
     */
    @Singular
    private List<ListViewItem> items;

    /**
     * 列表视图分页信息
     */
    private Pagination pagination;
}
