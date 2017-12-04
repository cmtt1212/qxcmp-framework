package com.qxcmp.framework.security.event;

import com.qxcmp.framework.security.Privilege;
import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminSecurityPrivilegeEnableEvent {

    private final User target;
    private final Privilege privilege;

    public AdminSecurityPrivilegeEnableEvent(User target, Privilege privilege) {
        this.target = target;
        this.privilege = privilege;
    }
}
