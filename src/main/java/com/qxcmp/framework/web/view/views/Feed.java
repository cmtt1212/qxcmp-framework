package com.qxcmp.framework.web.view.views;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;

import java.util.List;

/**
 * @author Aaric
 */
@Getter
public class Feed extends AbstractComponent {

    private List<com.qxcmp.framework.message.Feed> feeds;

    public Feed(List<com.qxcmp.framework.message.Feed> feeds) {
        this.feeds = feeds;
    }

    @Override
    public String getFragmentFile() {
        return "qxcmp/views/feed";
    }

    @Override
    public String getClassPrefix() {
        return "ui";
    }

    @Override
    public String getClassSuffix() {
        return "feed";
    }
}
