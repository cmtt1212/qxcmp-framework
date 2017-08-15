package com.qxcmp.framework.web.event;

import com.qxcmp.framework.view.dictionary.DictionaryView;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 平台首页扩展事件
 * <p>
 * 用于支持动态扩展首页字典内容
 *
 * @author aaric
 */
@AllArgsConstructor
@Data
public class HomePageExtensionEvent {

    /**
     * 字典视图构建器
     */
    private DictionaryView.DictionaryViewBuilder builder;
}
