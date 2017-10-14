package com.qxcmp.framework.message;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class SmsTemplateService extends AbstractEntityService<SmsTemplate, String, SmsTemplateRepository> {

    public SmsTemplateService(SmsTemplateRepository repository) {
        super(repository);
    }

    @Override
    protected <S extends SmsTemplate> String getEntityId(S entity) {
        return entity.getId();
    }
}
