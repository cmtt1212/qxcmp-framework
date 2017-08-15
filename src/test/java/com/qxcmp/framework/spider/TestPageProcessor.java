package com.qxcmp.framework.spider;

import com.qxcmp.framework.spdier.Spider;
import com.qxcmp.framework.spdier.SpiderPageProcessor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.Optional;

@Spider(name = "Test Baidu Spider", startUrls = "http://www.baidu.com", pipelines = TestPipeline.class)
@Component
public class TestPageProcessor extends SpiderPageProcessor<Object> {

    @Override
    protected boolean isTargetPage(Page page) {
        return false;
    }

    @Override
    protected Optional<Object> processTargetPage(Page page) {
        return Optional.empty();
    }

    @Override
    protected void processContentPage(Page page) {

    }
}