package com.qxcmp.framework.mall;

import com.qxcmp.framework.core.entity.AbstractEntityService;
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

    @Override
    public <S extends Commodity> Optional<S> create(Supplier<S> supplier) {
        S entity = supplier.get();

        entity.setSalesVolume(0);

        return super.create(() -> entity);
    }

    @Override
    protected <S extends Commodity> Long getEntityId(S entity) {
        return entity.getId();
    }
}
