package com.qxcmp.framework.link.event;

import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminLinkSettingsEvent {

    private final User target;

    public AdminLinkSettingsEvent(User target) {
        this.target = target;
    }
}
