package com.qxcmp.framework.domain;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 广告服务
 *
 * @author aaric
 */
@Service
public class AdvertisementService extends AbstractEntityService<Advertisement, Long, AdvertisementRepository> {

    public AdvertisementService(AdvertisementRepository repository) {
        super(repository);
    }

    public Optional<Advertisement> findOne(String id) {
        try {
            Long aId = Long.parseLong(id);
            return findOne(aId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Page<Advertisement> findByType(String type, Pageable pageable) {
        return repository.findByTypeOrderByAdOrderDesc(type, pageable);
    }

    /**
     * 从实体对象获取实体主键的方式，子类唯一需要实现的接口 该方法不能返回{null}值
     *
     * @param entity 实体对象
     *
     * @return 实体对象的主键
     */
    @Override
    protected <S extends Advertisement> Long getEntityId(S entity) {
        return entity.getId();
    }
}
