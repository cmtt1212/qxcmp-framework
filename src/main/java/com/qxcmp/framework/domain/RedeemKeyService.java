package com.qxcmp.framework.domain;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import com.qxcmp.framework.core.support.IDGenerator;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 兑换码服务
 *
 * @author aaric
 */
@Service
public class RedeemKeyService extends AbstractEntityService<RedeemKey, String, RedeemKeyRepository> {
    public RedeemKeyService(RedeemKeyRepository repository) {
        super(repository);
    }

    @Override
    public <S extends RedeemKey> Optional<S> create(Supplier<S> supplier) {
        S entity = supplier.get();

        entity.setId(IDGenerator.next());
        entity.setStatus(RedeemKeyStatus.NEW);
        entity.setDateCreated(new Date());

        return super.create(() -> entity);
    }

    @Override
    protected <S extends RedeemKey> String getEntityId(S entity) {
        return entity.getId();
    }
}
