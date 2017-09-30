package com.qxcmp.framework.mall;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import com.qxcmp.framework.core.support.IDGenerator;
import com.qxcmp.framework.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class StoreService extends AbstractEntityService<Store, String, StoreRepository> {

    public StoreService(StoreRepository repository) {
        super(repository);
    }

    public Page<Store> findByUser(User user, Pageable pageable) {
        return repository.findByUser(user, pageable);
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
