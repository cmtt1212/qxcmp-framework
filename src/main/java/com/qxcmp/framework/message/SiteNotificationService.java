package com.qxcmp.framework.message;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Aaric
 */
@Service
public class SiteNotificationService extends AbstractEntityService<SiteNotification, Long, SiteNotificationRepository> {

    public SiteNotificationService(SiteNotificationRepository repository) {
        super(repository);
    }

    public Optional<SiteNotification> findOne(String id) {
        try {
            return findOne(Long.parseLong(id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * 当前启用的网站通知
     *
     * @return 当前激活的网站通知中的第一个
     */
    public Optional<SiteNotification> findActiveNotifications() {
        return repository.findActive().stream().findAny();
    }

    @Override
    protected <S extends SiteNotification> Long getEntityId(S entity) {
        return entity.getId();
    }
}
