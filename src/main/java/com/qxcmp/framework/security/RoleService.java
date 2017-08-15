package com.qxcmp.framework.security;

import com.qxcmp.framework.entity.AbstractEntityService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * 角色服务
 *
 * @author aaric
 */
@Service
public class RoleService extends AbstractEntityService<Role, Long, RoleRepository> {

    public RoleService(RoleRepository repository) {
        super(repository);
    }

    public Optional<Role> findByName(String name) {
        return repository.findByName(name);
    }

    public Optional<Role> update(String name, Consumer<Role> consumer) {
        return findByName(name).flatMap(role -> update(role.getId(), consumer));
    }

    @Override
    protected <S extends Role> Long getEntityId(S entity) {
        return Optional.ofNullable(entity.getId()).orElse(-1L);
    }
}
