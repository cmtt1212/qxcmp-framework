package com.qxcmp.framework.statistics;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class AccessHistoryService extends AbstractEntityService<AccessHistory, Long, AccessHistoryRepository> {
    public AccessHistoryService(AccessHistoryRepository repository) {
        super(repository);
    }

    @Override
    protected <S extends AccessHistory> Long getEntityId(S entity) {
        return entity.getId();
    }
}
