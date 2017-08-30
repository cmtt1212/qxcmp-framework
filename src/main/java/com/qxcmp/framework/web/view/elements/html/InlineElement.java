package com.qxcmp.framework.web.view.elements.html;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
    public InlineElement(String fragmentName) {
        super(fragmentName);
    }
}
