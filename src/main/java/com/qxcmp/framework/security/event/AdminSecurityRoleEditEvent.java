package com.qxcmp.framework.security.event;

import com.qxcmp.framework.security.Role;
import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminSecurityRoleEditEvent {

    private final User target;
    private final Role role;

    public AdminSecurityRoleEditEvent(User target, Role role) {
        this.target = target;
        this.role = role;
    }
}