package com.qxcmp.framework.config;

import com.qxcmp.framework.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

/**
 * 系统字典项服务
 *
 * @author aaric
 */
@Service
public class SystemDictionaryItemService extends AbstractEntityService<SystemDictionaryItem, Long, SystemDictionaryItemRepository> {
    public SystemDictionaryItemService(SystemDictionaryItemRepository repository) {
        super(repository);
    }

    @Override
    protected <S extends SystemDictionaryItem> Long getEntityId(S entity) {
        return entity.getId();
    }
}
