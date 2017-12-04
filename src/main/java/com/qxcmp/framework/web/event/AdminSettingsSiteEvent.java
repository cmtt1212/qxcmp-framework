package com.qxcmp.framework.web.event;

import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminSettingsSiteEvent {

    private final User user;

    public AdminSettingsSiteEvent(User user) {
        this.user = user;
    }
}
