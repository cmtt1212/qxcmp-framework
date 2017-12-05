package com.qxcmp.mall;

import com.qxcmp.core.entity.AbstractEntityService;
import com.qxcmp.core.support.IDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 收货人服务
 *
 * @author aaric
 */
@Service
public class ConsigneeService extends AbstractEntityService<Consignee, String, ConsigneeRepository> {
    public ConsigneeService(ConsigneeRepository repository) {
        super(repository);
    }

    /**
     * 获取用户收货地址
     *
     * @param userId 用户ID
     * @return 用户收货地址
     */
    public List<Consignee> findByUser(String userId) {
        return repository.findByUserIdOrderByDateModifiedDesc(userId);
    }


    @Override
    public <S extends Consignee> Optional<S> create(Supplier<S> supplier) {
        S consignee = supplier.get();

        if (StringUtils.isNotEmpty(consignee.getId())) {
            return Optional.empty();
        }

        consignee.setId(IDGenerator.next());

        return super.create(() -> consignee);
    }

    @Override
    protected <S extends Consignee> String getEntityId(S entity) {
        return entity.getId();
    }
}
