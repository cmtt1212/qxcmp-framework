package com.qxcmp.framework.advertisement.event;

import com.qxcmp.framework.advertisement.Advertisement;
import com.qxcmp.framework.user.User;
import lombok.Data;

/**
 * @author Aaric
 */
@Data
public class AdminAdvertisementEditEvent {

    private final User user;
    private final Advertisement advertisement;

    public AdminAdvertisementEditEvent(User user, Advertisement advertisement) {
        this.user = user;
        this.advertisement = advertisement;
    }
}
