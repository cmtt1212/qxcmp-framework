package com.qxcmp.framework.web.view.elements.html;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 块级元素
 *
 * @author aaric
 */
@Getter
@Setter
public abstract class BlockElement extends BaseHtmlElement {
    public BlockElement(String fragmentName) {
        super(fragmentName);
    }
}
