package com.qxcmp.framework.message;

import com.qxcmp.framework.entity.AbstractEntityService;
import com.qxcmp.framework.support.IDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;


@Service
public class SystemMessageService extends AbstractEntityService<SystemMessage, Long, SystemMessageRepository>{

    public SystemMessageService(SystemMessageRepository repository) {
        super(repository);
    }

    public Page<SystemMessage> findByUserId(String userId, Pageable pageable){
        return repository.findByUserId(userId,pageable);
    }

    @Override
    public <S extends SystemMessage> Optional<S> create(Supplier<S> supplier) {
        S systemMessage = supplier.get();

        if (Objects.nonNull(systemMessage.getId())) {
            return Optional.empty();
        }

        return super.create(() -> systemMessage);
    }

    @Override
    protected <S extends SystemMessage> Long getEntityId(S entity) {
        return entity.getId();
    }
}
