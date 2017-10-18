package com.qxcmp.framework.link;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService extends AbstractEntityService<Link, Long, LinkRepository> {
    public LinkService(LinkRepository repository) {
        super(repository);
    }

    public List<Link> findByType(String type) {
        return repository.findByTypeOrderBySort(type);
    }

    @Override
    protected <S extends Link> Long getEntityId(S entity) {
        return entity.getId();
    }
}
