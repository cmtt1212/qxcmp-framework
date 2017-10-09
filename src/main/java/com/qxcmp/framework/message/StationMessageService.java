package com.qxcmp.framework.message;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class StationMessageService extends AbstractEntityService<StationMessage, Long, StationMessageRepository> {


    public StationMessageService(StationMessageRepository repository) {
        super(repository);
    }

    public Optional<StationMessage> findOne(String id) {
        try {
            Long aId = Long.parseLong(id);
            return findOne(aId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Page<StationMessage> findByUserID(String userID, Pageable pageable) {
        return repository.findByUserID(userID,pageable).orElse(null);
    }

    @Override
    public <S extends StationMessage> Optional<S> create(Supplier<S> supplier) {

        S entity = supplier.get();

        if (Objects.nonNull(entity.getId())) {
            return Optional.empty();
        }
        return super.create(() -> entity);
    }

    @Override
    protected <S extends StationMessage> Long getEntityId(S entity) {
        return entity.getId();
    }
}
