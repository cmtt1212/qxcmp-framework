package com.qxcmp.framework.user;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import com.qxcmp.framework.core.support.IDGenerator;
import com.qxcmp.framework.core.support.ImageGenerator;
import com.qxcmp.framework.domain.ImageService;
import com.qxcmp.framework.security.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 用户服务
 *
 * @author aaric
 */
@Service
public class UserService extends AbstractEntityService<User, String, UserRepository> {

    private final ImageService imageService;

    private final ImageGenerator imageGenerator;

    public UserService(UserRepository repository, ImageService imageService, ImageGenerator imageGenerator) {
        super(repository);
        this.imageService = imageService;
        this.imageGenerator = imageGenerator;
    }

    /**
     * 获取当前认证的用户
     *
     * @return {null} 如果用户为登录
     */
    public User currentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication)) {
            return null;
        }

        Object principle = authentication.getPrincipal();

        if (principle instanceof User) {
            return (User) principle;
        }

        return null;
    }

    /**
     * 判断用户是否含有某些权限
     *
     * @param user       用户
     * @param privileges 要判定的权限
     *
     * @return 如果用户拥有任意一个权限将返回真
     */
    public boolean hasRole(User user, String... privileges) {
        return user.getAuthorities().stream().anyMatch(authority -> {
            for (String privilege : privileges) {
                if (authority.getAuthority().equals("ROLE_" + privilege)) {
                    return true;
                }
            }

            return false;
        });
    }

    /**
     * 查找具有指定权限的用户
     *
     * @param privilege 拥有的权限
     *
     * @return 指定权限的用户
     */
    public List<User> findByAuthority(String privilege) {
        return repository.findByAuthority(privilege);
    }

    /**
     * 查找具有指定角色的用户
     *
     * @param role 拥有的角色
     *
     * @return 指定角色的用户
     */
    public List<User> findByRole(Role role) {
        return repository.findByRole(role);
    }

    public Optional<User> findById(String userId) {

        if (findByUsername(userId).isPresent()) {
            return findByUsername(userId);
        }

        if (findByEmail(userId).isPresent()) {
            return findByEmail(userId);
        }

        if (findByPhone(userId).isPresent()) {
            return findByPhone(userId);
        }

        if (findByOpenID(userId).isPresent()) {
            return findByOpenID(userId);
        }

        return Optional.empty();
    }

    public Optional<User> findByUsername(String username) {
        return StringUtils.isEmpty(username) ? Optional.empty() : repository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return StringUtils.isEmpty(email) ? Optional.empty() : repository.findByEmail(email);
    }

    public Optional<User> findByPhone(String phone) {
        return StringUtils.isEmpty(phone) ? Optional.empty() : repository.findByPhone(phone);
    }

    public Optional<User> findByOpenID(String openId) {
        return StringUtils.isEmpty(openId) ? Optional.empty() : repository.findByOpenID(openId);
    }

    public Page<User> findWeixinUser(Pageable pageable) {
        return repository.findWeixinUser(pageable);
    }

    /**
     * 为用户生成默认头像
     *
     * @param user 用户
     */
    public void setDefaultPortrait(User user) {
        try {

            String name = user.getName();

            if (StringUtils.isBlank(name)) {
                name = user.getNickname();
            }

            if (StringUtils.isBlank(name)) {
                name = user.getUsername();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(imageGenerator.nextPortrait(name, 96), "jpg", byteArrayOutputStream);
            imageService.store(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), "jpg", 96, 96).ifPresent(image -> user.setPortrait(String.format("/api/image/%s.%s", image.getId(), image.getType())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <S extends User> Optional<S> create(Supplier<S> supplier) {
        S user = supplier.get();

        if (StringUtils.isNotEmpty(user.getId())) {
            return Optional.empty();
        }

        user.setId(IDGenerator.next());

        return super.create(() -> user);
    }

    @Override
    protected <S extends User> String getEntityId(S entity) {
        return entity.getId();
    }

}
