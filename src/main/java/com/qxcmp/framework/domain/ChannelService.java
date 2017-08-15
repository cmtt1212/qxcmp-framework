package com.qxcmp.framework.domain;

import com.google.common.collect.Lists;
import com.qxcmp.framework.entity.AbstractEntityService;
import com.qxcmp.framework.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 文章栏目服务
 *
 * @author aaric
 */
@Service
public class ChannelService extends AbstractEntityService<Channel, Long, ChannelRepository> {
    public ChannelService(ChannelRepository repository) {
        super(repository);
    }

    /**
     * 获取用户ID拥有的栏目
     * <p>
     * 当用户为栏目所有者或者管理员的时候将会返回该栏目
     *
     * @param user 用户ID
     * @return 用户栏目列表
     */
    public List<Channel> findByUserId(User user) {
        List<Channel> channels = Lists.newArrayList(repository.findByOwner(user));
        channels.addAll(repository.findByAdminsContains(user));
        return channels;
    }

    public Optional<Channel> findByAlias(String alias) {
        return repository.findByAlias(alias);
    }

    public List<Channel> findByCatalog(String catalog) {
        return repository.findByCatalog(catalog);
    }

    public List<Channel> findByCatalogContains(Set<String> catalogs) {
        return repository.findByCatalogContains(catalogs);
    }

    public Optional<Channel> findOne(String id) {
        try {
            Long cId = Long.parseLong(id);
            return findOne(cId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    protected <S extends Channel> Long getEntityId(S entity) {
        return entity.getId();
    }
}
