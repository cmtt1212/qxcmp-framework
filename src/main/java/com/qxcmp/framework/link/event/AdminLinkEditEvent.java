package com.qxcmp.framework.link.event;

import com.qxcmp.framework.link.Link;
import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminLinkEditEvent {

    private final User target;
    private final Link link;

    public AdminLinkEditEvent(User target, Link link) {
        this.target = target;
        this.link = link;
    }
}
