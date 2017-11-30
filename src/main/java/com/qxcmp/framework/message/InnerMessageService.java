package com.qxcmp.framework.message;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class InnerMessageService extends AbstractEntityService<InnerMessage, Long, InnerMessageRepository> {


    public InnerMessageService(InnerMessageRepository repository) {
        super(repository);
    }

    public Optional<InnerMessage> findOne(String id) {
        try {
            Long aId = Long.parseLong(id);
            return findOne(aId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Page<InnerMessage> findByUserID(String userID, Pageable pageable) {
        return repository.findByUserID(userID, pageable);
    }

    @Override
    public <S extends InnerMessage> Optional<S> create(Supplier<S> supplier) {

        S entity = supplier.get();

        if (Objects.nonNull(entity.getId())) {
            return Optional.empty();
        }
        return super.create(() -> entity);
    }

    @Override
    protected <S extends InnerMessage> Long getEntityId(S entity) {
        return entity.getId();
    }
}
