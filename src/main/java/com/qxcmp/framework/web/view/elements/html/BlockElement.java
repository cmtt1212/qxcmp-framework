package com.qxcmp.framework.web.view.elements.html;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 块级元素
 *
 * @author aaric
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BlockElement extends BaseHtmlElement {
    public BlockElement(String fragmentName) {
        super(fragmentName);
    }
}
