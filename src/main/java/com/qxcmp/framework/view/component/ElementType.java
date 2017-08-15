package com.qxcmp.framework.view.component;

/**
 * HTML 基本元素类型
 *
 * @author aaric
 */
public enum ElementType {
    H1("h1"),
    H2("h2"),
    H3("h3"),
    H4("h4"),
    H5("h5"),
    H6("h6"),
    P("p"),
    B("b"),
    CODE("code"),
    PRE("pre"),;

    private String element;

    ElementType(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
