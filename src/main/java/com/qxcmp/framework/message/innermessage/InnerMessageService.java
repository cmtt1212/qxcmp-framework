package com.qxcmp.framework.message.innermessage;

import com.qxcmp.framework.account.username.SecurityQuestion;
import com.qxcmp.framework.entity.AbstractEntityService;
import com.qxcmp.framework.support.IDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;


@Service
public class InnerMessageService extends AbstractEntityService<InnerMessage, String, InnerMessageRepository>{

    public InnerMessageService(InnerMessageRepository repository) {
        super(repository);
    }

    public Optional<InnerMessage> findByUserId(String userId){
        return repository.findByUserId(userId);
    }

    @Override
    public <S extends InnerMessage> Optional<S> create(Supplier<S> supplier) {
        S innerMessage = supplier.get();

        if (StringUtils.isNotEmpty(innerMessage.getId())) {
            return Optional.empty();
        }

        innerMessage.setId(IDGenerator.next());

        return super.create(() -> innerMessage);
    }

    @Override
    protected <S extends InnerMessage> String getEntityId(S entity) {
        return entity.getId();
    }
}
