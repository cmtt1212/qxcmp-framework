package com.qxcmp.framework.advertisement.event;

import com.qxcmp.framework.advertisement.Advertisement;
import com.qxcmp.framework.config.SiteService;
import com.qxcmp.framework.message.MessageService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.qxcmp.framework.core.QxcmpSecurityConfiguration.PRIVILEGE_ADMIN_ADVERTISEMENT;

/**
 * @author Aaric
 */
@Component
@RequiredArgsConstructor
public class AdminAdvertisementEventListener {

    private final MessageService messageService;
    private final UserService userService;
    private final SiteService siteService;

    @EventListener
    public void onNewEvent(AdminAdvertisementNewEvent event) {
        Advertisement advertisement = event.getAdvertisement();
        List<User> feedUsers = userService.findByAuthority(PRIVILEGE_ADMIN_ADVERTISEMENT);
        feedUsers.add(event.getUser());

        messageService.feed(feedUsers.stream().map(User::getId).collect(Collectors.toList()), event.getUser(), String.format("%s 新建了一条广告 <a href='https://%s'>%s</a>",
                event.getUser().getDisplayName(),
                siteService.getDomain() + "/admin/advertisement/" + advertisement.getId() + "/edit",
                StringUtils.isNotBlank(advertisement.getTitle()) ? advertisement.getTitle() : "无名称"));
    }

    @EventListener
    public void onEditEvent(AdminAdvertisementEditEvent event) {
        Advertisement advertisement = event.getAdvertisement();
        List<User> feedUsers = userService.findByAuthority(PRIVILEGE_ADMIN_ADVERTISEMENT);
        feedUsers.add(event.getUser());

        messageService.feed(feedUsers.stream().map(User::getId).collect(Collectors.toList()), event.getUser(), String.format("%s 编辑了广告 <a href='https://%s'>%s</a>",
                event.getUser().getDisplayName(),
                siteService.getDomain() + "/admin/advertisement/" + advertisement.getId() + "/edit",
                StringUtils.isNotBlank(advertisement.getTitle()) ? advertisement.getTitle() : "无名称"));
    }
}
