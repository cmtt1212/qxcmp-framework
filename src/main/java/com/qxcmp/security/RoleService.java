package com.qxcmp.security;

import com.qxcmp.core.entity.AbstractEntityService;
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

    public Optional<Role> findOne(String id) {
        try {
            return findOne(Long.parseLong(id));
        } catch (Exception e) {
            return Optional.empty();
        }
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
