package com.qxcmp.framework.security.event;

import com.qxcmp.framework.security.Privilege;
import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminSecurityPrivilegeDisableEvent {

    private final User target;
    private final Privilege privilege;

    public AdminSecurityPrivilegeDisableEvent(User target, Privilege privilege) {
        this.target = target;
        this.privilege = privilege;
    }
}
