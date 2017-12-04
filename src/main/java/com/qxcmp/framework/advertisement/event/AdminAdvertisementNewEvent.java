package com.qxcmp.framework.advertisement.event;

import com.qxcmp.framework.advertisement.Advertisement;
import com.qxcmp.framework.user.User;
import lombok.Data;

/**
 * @author Aaric
 */
@Data
public class AdminAdvertisementNewEvent {

    private final User user;
    private final Advertisement advertisement;

    public AdminAdvertisementNewEvent(User user, Advertisement advertisement) {
        this.user = user;
        this.advertisement = advertisement;
    }
}
