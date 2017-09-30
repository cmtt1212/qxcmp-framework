package com.qxcmp.framework.mall;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import com.qxcmp.framework.core.support.IDGenerator;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.function.Supplier;

public class StoreService extends AbstractEntityService<Store, String, StoreRepository> {

    public StoreService(StoreRepository repository) {
        super(repository);
    }

    @Override
    public <S extends Store> Optional<S> create(Supplier<S> supplier) {
        S store = supplier.get();

        if (StringUtils.isNotEmpty(store.getId())) {
            return Optional.empty();
        }

        store.setId(IDGenerator.next());

        return super.create(() -> store);

    }

    @Override
    protected <S extends Store> String getEntityId(S entity) {
        return entity.getId();
    }
}
