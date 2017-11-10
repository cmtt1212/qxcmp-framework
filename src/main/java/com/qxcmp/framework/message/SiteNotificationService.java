package com.qxcmp.framework.message;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class SiteNotificationService extends AbstractEntityService<SiteNotification, Long, SiteNotificationRepository> {

    public SiteNotificationService(SiteNotificationRepository repository) {
        super(repository);
    }

    @Override
    protected <S extends SiteNotification> Long getEntityId(S entity) {
        return entity.getId();
    }
}
