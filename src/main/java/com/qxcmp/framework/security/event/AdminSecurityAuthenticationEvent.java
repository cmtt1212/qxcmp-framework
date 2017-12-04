package com.qxcmp.framework.security.event;

import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminSecurityAuthenticationEvent {

    private final User target;

    public AdminSecurityAuthenticationEvent(User target) {
        this.target = target;
    }
}
