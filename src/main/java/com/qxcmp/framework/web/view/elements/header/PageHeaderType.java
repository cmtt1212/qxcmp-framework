package com.qxcmp.framework.web.view.elements.header;

public enum PageHeaderType {
    H1("h1"),
    H2("h2"),
    H3("h3"),
    H4("h4"),
    H5("h5"),
    H6("h6");

    private String tag;

    PageHeaderType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
