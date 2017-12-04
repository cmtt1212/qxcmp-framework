package com.qxcmp.framework.redeem.event;

import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminRedeemGenerateEvent {

    private final User target;
    private final String name;
    private final int count;

    public AdminRedeemGenerateEvent(User target, String name, int count) {
        this.target = target;
        this.name = name;
        this.count = count;
    }
}
