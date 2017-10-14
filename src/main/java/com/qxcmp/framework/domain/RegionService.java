package com.qxcmp.framework.domain;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class RegionService extends AbstractEntityService<Region, String, RegionRepository> {
    public RegionService(RegionRepository repository) {
        super(repository);
    }

    @Override
    protected <S extends Region> String getEntityId(S entity) {
        return entity.getCode();
    }
}
