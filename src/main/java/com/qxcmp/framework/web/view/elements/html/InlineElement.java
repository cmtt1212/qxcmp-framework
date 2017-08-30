package com.qxcmp.framework.web.view.elements.html;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 内联HTML元素
 *
 * @author aaric
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class InlineElement extends BaseHtmlElement {
    public InlineElement(String fragmentName) {
        super(fragmentName);
    }
}
