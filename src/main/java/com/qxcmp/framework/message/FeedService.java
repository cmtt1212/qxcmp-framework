package com.qxcmp.framework.message;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

/**
 * @author Aaric
 */
@Service
public class FeedService extends AbstractEntityService<Feed, Long, FeedRepository> {
    public FeedService(FeedRepository repository) {
        super(repository);
    }

    @Override
    protected <S extends Feed> Long getEntityId(S entity) {
        return entity.getId();
    }
}
