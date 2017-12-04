package com.qxcmp.framework.web.event;

import com.qxcmp.framework.config.SystemDictionary;
import com.qxcmp.framework.user.User;
import lombok.Getter;

/**
 * @author Aaric
 */
@Getter
public class AdminSettingsDictionaryEvent {

    private final User user;
    private final SystemDictionary systemDictionary;

    public AdminSettingsDictionaryEvent(User user, SystemDictionary systemDictionary) {
        this.user = user;
        this.systemDictionary = systemDictionary;
    }
}
