package com.qxcmp.framework.weixin.event;

import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class WeixinUserSyncFinishEvent {

    private final User target;
    private final long totalUser;

    public WeixinUserSyncFinishEvent(User target, long totalUser) {
        this.target = target;
        this.totalUser = totalUser;
    }
}
