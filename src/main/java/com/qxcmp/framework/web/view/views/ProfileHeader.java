package com.qxcmp.framework.web.view.views;

import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileHeader extends AbstractComponent {

    private User user;

    public ProfileHeader(User user) {
        this.user = user;
    }

    @Override
    public String getFragmentFile() {
        return "qxcmp/views/profile";
    }
}
