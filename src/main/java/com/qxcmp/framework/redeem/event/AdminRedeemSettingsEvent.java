package com.qxcmp.framework.redeem.event;

import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminRedeemSettingsEvent {

    private final User target;

    public AdminRedeemSettingsEvent(User target) {
        this.target = target;
    }
}
