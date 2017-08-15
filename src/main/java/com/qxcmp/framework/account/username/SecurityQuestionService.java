package com.qxcmp.framework.account.username;

import com.qxcmp.framework.entity.AbstractEntityService;
import com.qxcmp.framework.support.IDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * 密保问题服务
 *
 * @author aaric
 */
@Service
public class SecurityQuestionService extends AbstractEntityService<SecurityQuestion, String, SecurityQuestionRepository> {

    public SecurityQuestionService(SecurityQuestionRepository repository) {
        super(repository);
    }

    public Optional<SecurityQuestion> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public <S extends SecurityQuestion> Optional<S> create(Supplier<S> supplier) {
        S securityQuestion = supplier.get();

        if (StringUtils.isNotEmpty(securityQuestion.getId())) {
            return Optional.empty();
        }

        securityQuestion.setId(IDGenerator.next());

        return super.create(() -> securityQuestion);
    }

    @Override
    protected <S extends SecurityQuestion> String getEntityId(S entity) {
        return entity.getId();
    }
}
