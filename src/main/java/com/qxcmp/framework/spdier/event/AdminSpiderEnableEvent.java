package com.qxcmp.framework.spdier.event;

import com.qxcmp.framework.spdier.SpiderDefinition;
import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminSpiderEnableEvent {

    private final User user;
    private final SpiderDefinition spiderDefinition;

    public AdminSpiderEnableEvent(User user, SpiderDefinition spiderDefinition) {
        this.user = user;
        this.spiderDefinition = spiderDefinition;
    }
}
