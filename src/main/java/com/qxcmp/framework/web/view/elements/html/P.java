package com.qxcmp.framework.web.view.elements.html;

import lombok.Getter;
import lombok.Setter;

/**
 * 段落
 *
 * @author aaric
 */
@Getter
@Setter
public class P extends BlockElement {
    public P(String text) {
        super(text);
    }

    @Override
    public String getFragmentName() {
        return "p";
    }
}
