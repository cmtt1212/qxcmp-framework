package com.qxcmp.framework.weixin.event;

import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminWeixinSettingsEvent {

    private final User target;

    public AdminWeixinSettingsEvent(User target) {
        this.target = target;
    }
}
