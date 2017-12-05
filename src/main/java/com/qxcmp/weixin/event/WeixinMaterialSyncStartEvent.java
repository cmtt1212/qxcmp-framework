package com.qxcmp.weixin.event;

import com.qxcmp.user.User;
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
