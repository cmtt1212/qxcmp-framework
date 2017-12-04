package com.qxcmp.framework.weixin.event;

import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class WeixinUserSyncStartEvent {

    private final User target;

    public WeixinUserSyncStartEvent(User target) {
        this.target = target;
    }
}
