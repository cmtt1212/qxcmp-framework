package com.qxcmp.framework.spdier.event;

import com.qxcmp.framework.spdier.SpiderRuntime;
import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminSpiderStopEvent {

    private final User user;
    private final SpiderRuntime spiderRuntime;

    public AdminSpiderStopEvent(User user, SpiderRuntime spiderRuntime) {
        this.user = user;
        this.spiderRuntime = spiderRuntime;
    }
}
