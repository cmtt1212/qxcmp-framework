package com.qxcmp.framework.domain;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WechatMpNewsArticleService extends AbstractEntityService<WechatMpNewsArticle, Long, WechatMpNewsArticleRepository> {

    public WechatMpNewsArticleService(WechatMpNewsArticleRepository repository) {
        super(repository);
    }

    public Optional<WechatMpNewsArticle> findOne(String id) {
        try {
            Long aId = Long.parseLong(id);
            return findOne(aId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<WechatMpNewsArticle> findByUrl(String url) {
        return repository.findByUrl(url);
    }

    @Override
    protected <S extends WechatMpNewsArticle> Long getEntityId(S entity) {
        return entity.getId();
    }
}
