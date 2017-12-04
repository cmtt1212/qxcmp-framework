package com.qxcmp.framework.weixin.event;

import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class WeixinMaterialSyncStartEvent {

    private final User target;

    public WeixinMaterialSyncStartEvent(User target) {
        this.target = target;
    }
}
