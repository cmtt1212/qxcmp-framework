package com.qxcmp.framework.statistics;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccessHistoryService extends AbstractEntityService<AccessHistory, Long, AccessHistoryRepository> {
    public AccessHistoryService(AccessHistoryRepository repository) {
        super(repository);
    }

    @Override
    protected <S extends AccessHistory> Long getEntityId(S entity) {
        return entity.getId();
    }

    public Page<AccessHistoryPageResult> findByDateCreatedAfter(Date date, Pageable pageable) {
        return repository.findByDateCreatedAfter(date, pageable);
    }
}
