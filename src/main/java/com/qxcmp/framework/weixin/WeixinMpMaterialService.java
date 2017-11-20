package com.qxcmp.framework.weixin;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

/**
 * @author Aaric
 */
@Service
public class WeixinMpMaterialService extends AbstractEntityService<WeixinMpMaterial, String, WeixinMpMaterialRepository> {
    public WeixinMpMaterialService(WeixinMpMaterialRepository repository) {
        super(repository);
    }

    @Override
    protected <S extends WeixinMpMaterial> String getEntityId(S entity) {
        return entity.getId();
    }
}
