package com.qxcmp.framework.web.view.elements.html;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

/**
 * 基本HTML元素
 * <p>
 * 基本元素不支持嵌套，如果嵌套需要自行组合
 *
 * @author aaric
 */
@Getter
@Setter
public abstract class BaseHtmlElement extends AbstractComponent {

    /**
     * 元素 CSS class 名称
     * <p>
     * 该属性用于定制化一些基本HTML元素
     * <p>
     * 当需要在基本元素上增加一些 class 名称的时候使用该属性
     */
    private String className;

    /**
     * 元素文本
     */
    private String text;

    public BaseHtmlElement() {
    }

    public BaseHtmlElement(String text) {
        this.text = text;
    }

    @Override
    public String getFragmentFile() {
        return "qxcmp/globals/html";
    }
}
