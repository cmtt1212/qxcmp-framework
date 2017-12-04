package com.qxcmp.framework.weixin.event;

import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class WeixinMaterialSyncFinishEvent {

    private final User target;
    private final long totalCount;

    public WeixinMaterialSyncFinishEvent(User target, long totalCount) {
        this.target = target;
        this.totalCount = totalCount;
    }
}
