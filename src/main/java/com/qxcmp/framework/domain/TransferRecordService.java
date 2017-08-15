package com.qxcmp.framework.domain;

import com.qxcmp.framework.entity.AbstractEntityService;
import com.qxcmp.framework.support.IDGenerator;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 转账记录实体服务
 *
 * @author aaric
 */
@Service
public class TransferRecordService extends AbstractEntityService<TransferRecord, String, TransferRecordRepository> {

    public TransferRecordService(TransferRecordRepository repository) {
        super(repository);
    }

    @Override
    public <S extends TransferRecord> Optional<S> create(Supplier<S> supplier) {
        S entity = supplier.get();

        entity.setId(IDGenerator.order());
        entity.setDateCreated(new Date());

        return super.create(() -> entity);
    }

    /**
     * 从实体对象获取实体主键的方式，子类唯一需要实现的接口 该方法不能返回{null}值
     *
     * @param entity 实体对象
     *
     * @return 实体对象的主键
     */
    @Override
    protected <S extends TransferRecord> String getEntityId(S entity) {
        return entity.getId();
    }
}
