package com.qxcmp.framework.spdier.event;

import com.qxcmp.framework.spdier.SpiderDefinition;
import com.qxcmp.framework.spdier.SpiderPageProcessor;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminSpiderFinishEvent {

    private final SpiderDefinition spiderDefinition;
    private final SpiderPageProcessor spiderPageProcessor;

    public AdminSpiderFinishEvent(SpiderDefinition spiderDefinition, SpiderPageProcessor spiderPageProcessor) {
        this.spiderDefinition = spiderDefinition;
        this.spiderPageProcessor = spiderPageProcessor;
    }
}
