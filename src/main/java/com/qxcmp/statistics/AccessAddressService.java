package com.qxcmp.statistics;

import com.qxcmp.core.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

/**
 * @author Aaric
 */
@Service
public class AccessAddressService extends AbstractEntityService<AccessAddress, String, AccessAddressRepository> {
    public AccessAddressService(AccessAddressRepository repository) {
        super(repository);
    }

    @Override
    protected <S extends AccessAddress> String getEntityId(S entity) {
        return entity.getAddress();
    }
}
