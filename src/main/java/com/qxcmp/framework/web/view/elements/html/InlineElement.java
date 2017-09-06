package com.qxcmp.framework.web.view.elements.html;

import lombok.Getter;
import lombok.Setter;

/**
 * 内联HTML元素
 *
 * @author aaric
 */
@Getter
@Setter
public abstract class InlineElement extends BaseHtmlElement {
    public InlineElement() {
    }

    public InlineElement(String text) {
        super(text);
    }
}
