package com.qxcmp.framework.mall;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * 商品实体服务
 *
 * @author aaric
 */
@Service
public class CommodityService extends AbstractEntityService<Commodity, Long, CommodityRepository> {
    public CommodityService(CommodityRepository repository) {
        super(repository);
    }

    public Optional<Commodity> findOne(String id) {
        try {
            Long cId = Long.parseLong(id);
            return findOne(cId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Page<Commodity> findByStore(Store store, Pageable pageable) {
        return repository.findByStore(store, pageable);
    }

    @Override
    public <S extends Commodity> Optional<S> create(Supplier<S> supplier) {
        S entity = supplier.get();

        return super.create(() -> entity);
    }

    @Override
    protected <S extends Commodity> Long getEntityId(S entity) {
        return entity.getId();
    }
}
