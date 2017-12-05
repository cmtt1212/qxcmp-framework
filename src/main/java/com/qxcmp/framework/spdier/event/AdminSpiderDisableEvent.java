package com.qxcmp.framework.spdier.event;

import com.qxcmp.framework.spdier.SpiderDefinition;
import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminSpiderDisableEvent {

    private final User user;
    private final SpiderDefinition spiderDefinition;

    public AdminSpiderDisableEvent(User user, SpiderDefinition spiderDefinition) {
        this.user = user;
        this.spiderDefinition = spiderDefinition;
    }
}
