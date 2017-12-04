package com.qxcmp.framework.weixin.event;

import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminWeixinMenuEvent {

    private final User target;

    public AdminWeixinMenuEvent(User target) {
        this.target = target;
    }
}
