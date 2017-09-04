package com.qxcmp.framework.web.view.elements.html;

import lombok.Getter;
import lombok.Setter;

/**
 * 标题2
 *
 * @author aaric
 */
@Getter
@Setter
public class H2 extends HeaderElement {

    public H2(String text) {
        super(text);
    }

    @Override
    public String getFragmentName() {
        return "h2";
    }
}
