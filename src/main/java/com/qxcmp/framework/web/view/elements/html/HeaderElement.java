package com.qxcmp.framework.web.view.elements.html;

import lombok.Getter;
import lombok.Setter;

/**
 * HTML标题元素
 *
 * @author aaric
 */
@Getter
@Setter
public abstract class HeaderElement extends BlockElement {
    public HeaderElement(String text) {
        super(text);
    }
}
