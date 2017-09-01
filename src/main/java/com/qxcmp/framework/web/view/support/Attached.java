package com.qxcmp.framework.web.view.support;

/**
 * 附着方式
 *
 * @author Aaric
 */
public enum Attached {
    NONE(""),
    ATTACHED(" attached"),
    TOP(" top attached"),
    BOTTOM(" bottom attached");

    private String value;

    Attached(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
