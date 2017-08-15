package com.qxcmp.framework.view.dictionary;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Map;

/**
 * 字典视图
 * <p>
 * 用于视图层渲染字典
 * <p>
 * 支持使用构建器模式
 *
 * @author aaric
 */
@Data
@Builder
public class DictionaryView {

    /**
     * 字典名称
     */
    private String title;

    /**
     * 字典内容
     */
    @Singular("dictionary")
    private Map<String, String> dictionary;
}
