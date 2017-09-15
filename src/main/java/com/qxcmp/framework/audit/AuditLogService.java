package com.qxcmp.framework.audit;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 审计日志服务
 *
 * @author aaric
 * @see AbstractEntityService
 */
@Service
public class AuditLogService extends AbstractEntityService<AuditLog, Long, AuditLogRepository> {

    public AuditLogService(AuditLogRepository repository) {
        super(repository);
    }

    @Override
    public Page<AuditLog> findAll(Pageable pageable) {
        return repository.findAllByOrderByDateCreatedDesc(pageable);
    }

    /**
     * 从实体对象获取实体主键的方式，子类唯一需要实现的接口
     * 该方法不能返回{null}值
     *
     * @param entity 实体对象
     *
     * @return 实体对象的主键
     */
    @Override
    protected <S extends AuditLog> Long getEntityId(S entity) {
        return entity.getId();
    }
}
