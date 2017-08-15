package com.qxcmp.framework.domain;

import com.qxcmp.framework.entity.AbstractEntityService;
import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.support.IDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * 钱包实体服务
 *
 * @author aaric
 */
@Service
public class WalletService extends AbstractEntityService<Wallet, String, WalletRepository> {

    private final UserService userService;

    public WalletService(WalletRepository repository, UserService userService) {
        super(repository);
        this.userService = userService;
    }

    /**
     * 根据用户ID获取钱包 如果用户存在但是钱包不存在，则返回新创建的钱包
     *
     * @param userId 用户ID
     *
     * @return 属于用于的钱包，如果用于不存在则返回empty
     */
    public Optional<Wallet> getByUserId(String userId) {
        return userService.findOne(userId).map(user -> {
            Optional<Wallet> walletOptional = repository.findByUserId(userId);

            if (walletOptional.isPresent()) {
                return walletOptional;
            } else {
                return create(() -> {
                    Wallet wallet = next();
                    wallet.setUserId(userId);
                    return wallet;
                });
            }

        }).orElse(Optional.empty());
    }

    @Override
    public <S extends Wallet> Optional<S> create(Supplier<S> supplier) {
        S wallet = supplier.get();

        if (StringUtils.isNotEmpty(wallet.getId())) {
            return Optional.empty();
        }

        wallet.setId(IDGenerator.next());

        return super.create(() -> wallet);
    }

    @Override
    protected <S extends Wallet> String getEntityId(S entity) {
        return entity.getId();
    }
}
