package com.qxcmp.framework.message;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    protected <S extends SiteNotification> Long getEntityId(S entity) {
        return entity.getId();
    }
}
