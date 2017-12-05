package com.qxcmp.account.username;

import com.qxcmp.core.entity.AbstractEntityService;
import com.qxcmp.core.support.IDGenerator;
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
public class AccountSecurityQuestionService extends AbstractEntityService<AccountSecurityQuestion, String, AccountSecurityQuestionRepository> {

    public AccountSecurityQuestionService(AccountSecurityQuestionRepository repository) {
        super(repository);
    }

    public Optional<AccountSecurityQuestion> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public <S extends AccountSecurityQuestion> Optional<S> create(Supplier<S> supplier) {
        S securityQuestion = supplier.get();

        if (StringUtils.isNotEmpty(securityQuestion.getId())) {
            return Optional.empty();
        }

        securityQuestion.setId(IDGenerator.next());

        return super.create(() -> securityQuestion);
    }

    @Override
    protected <S extends AccountSecurityQuestion> String getEntityId(S entity) {
        return entity.getId();
    }
}
