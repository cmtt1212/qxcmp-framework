package com.qxcmp.framework.link.event;

import com.qxcmp.framework.link.Link;
import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminLinkNewEvent {

    private final User target;
    private final Link link;

    public AdminLinkNewEvent(User target, Link link) {
        this.target = target;
        this.link = link;
    }
}
