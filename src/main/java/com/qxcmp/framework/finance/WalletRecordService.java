package com.qxcmp.framework.finance;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WalletRecordService extends AbstractEntityService<WalletRecord, Long, WalletRecordRepository> {
    public WalletRecordService(WalletRecordRepository repository) {
        super(repository);
    }

    public Page<WalletRecord> findByUserIdAndType(String userId, String type, Pageable pageable) {
        return repository.findByUserIdAndTypeOrderByDate(userId, type, pageable);
    }

    @Override
    protected <S extends WalletRecord> Long getEntityId(S entity) {
        return entity.getId();
    }
}
