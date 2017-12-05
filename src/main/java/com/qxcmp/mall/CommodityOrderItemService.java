package com.qxcmp.mall;

import com.qxcmp.core.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

/**
 * 商品订单项目服务
 *
 * @author aaric
 */
@Service
public class CommodityOrderItemService extends AbstractEntityService<CommodityOrderItem, Long, CommodityOrderItemRepository> {

    public CommodityOrderItemService(CommodityOrderItemRepository repository) {
        super(repository);
    }

    @Override
    protected <S extends CommodityOrderItem> Long getEntityId(S entity) {
        return entity.getId();
    }
}
