package com.qxcmp.framework.mall;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class CommodityVersionService extends AbstractEntityService<CommodityVersion, Long, CommodityVersionRepository> {
    public CommodityVersionService(CommodityVersionRepository repository) {
        super(repository);
    }

    @Override
    protected <S extends CommodityVersion> Long getEntityId(S entity) {
        return entity.getId();
    }
}
