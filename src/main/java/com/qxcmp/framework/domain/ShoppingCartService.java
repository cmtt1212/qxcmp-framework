package com.qxcmp.framework.domain;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import com.qxcmp.framework.exception.ShoppingCartServiceException;
import com.qxcmp.framework.core.support.IDGenerator;
import com.qxcmp.framework.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * 购物车服务
 *
 * @author aaric
 */
@Service
public class ShoppingCartService extends AbstractEntityService<ShoppingCart, String, ShoppingCartRepository> {

    private final UserService userService;

    private final CommodityService commodityService;

    private final ShoppingCartItemService shoppingCartItemService;

    public ShoppingCartService(ShoppingCartRepository repository, UserService userService, CommodityService commodityService, ShoppingCartItemService shoppingCartItemService) {
        super(repository);
        this.userService = userService;
        this.commodityService = commodityService;
        this.shoppingCartItemService = shoppingCartItemService;
    }

    /**
     * 获取用户购物车
     * <p>
     * 如果用户存在但是购物车不存在，则返回新创建的购物车
     *
     * @param userId 用户ID
     *
     * @return 用户购物车
     *
     * @throws ShoppingCartServiceException 如果购物车创建失败，抛出该异常
     */
    public ShoppingCart findByUserId(String userId) throws ShoppingCartServiceException {
        Optional<ShoppingCart> shoppingCartOptional = repository.findByUserId(userId);

        if (shoppingCartOptional.isPresent()) {
            return shoppingCartOptional.get();
        } else {
            return create(() -> {
                ShoppingCart shoppingCart = next();
                shoppingCart.setUserId(userId);
                return shoppingCart;
            }).orElseThrow(() -> new ShoppingCartServiceException("Can't create shopping cart"));
        }

    }

    @Override
    public <S extends ShoppingCart> Optional<S> create(Supplier<S> supplier) {
        S shoppingCart = supplier.get();

        if (StringUtils.isNotEmpty(shoppingCart.getId())) {
            return Optional.empty();
        }

        shoppingCart.setId(IDGenerator.next());

        return super.create(() -> shoppingCart);
    }

    @Override
    protected <S extends ShoppingCart> String getEntityId(S entity) {
        return entity.getId();
    }

}
