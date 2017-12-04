package com.qxcmp.framework.message;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aaric
 */
@Service
public class FeedService extends AbstractEntityService<Feed, Long, FeedRepository> {
    public FeedService(FeedRepository repository) {
        super(repository);
    }

    /**
     * 查询用户的Feed流
     *
     * @param userId 用户ID
     * @return 用户Feed流列表
     */
    public List<Feed> findByOwner(String userId) {
        return repository.findByOwnerAndDateCreatedAfterOrderByDateCreatedDesc(userId, DateTime.now().minusDays(30).toDate());
    }

    @Override
    protected <S extends Feed> Long getEntityId(S entity) {
        return entity.getId();
    }
}
