package com.qxcmp.framework.domain;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * 标签服务
 *
 * @author aaric
 */
@Service
public class LabelService extends AbstractEntityService<Label, Long, LabelRepository> {
    public LabelService(LabelRepository repository) {
        super(repository);
    }

    @Override
    public <S extends Label> Optional<S> create(Supplier<S> supplier) {
        Label label = supplier.get();
        label.setDateCreated(new Date());
        label.setDateModified(new Date());
        return super.create(supplier);
    }

    @Override
    public <S extends Label> Optional<S> update(Long id, Consumer<S> present) {
        checkNotNull(id, "Entity ID can't be null");

        @SuppressWarnings("unchecked")
        Optional<S> entity = (Optional<S>) findOne(id);

        if (entity.isPresent()) {

            present.accept(entity.get());

            checkState(getEntityId(entity.get()).equals(id), "Can't change entity ID while update entity");


            return entity.map(s -> {
                s.setDateModified(new Date());

                /*
                 * 如果标签重命名失败则返回空
                 * */
                try {
                    return repository.save(s);
                } catch (DataIntegrityViolationException e) {
                    return null;
                }
            });
        } else {
            return Optional.empty();
        }
    }

    /**
     * 创建一个标签，如果标签存在则返回 {@link Optional#empty()}
     *
     * @param type 标签类型
     * @param name 标签名称
     * @return 创建后的标签，如果标签存在则返回 {@link Optional#empty()}
     */
    public Optional<Label> create(String type, String name) {
        if (repository.findByTypeAndName(type, name).isPresent()) {
            return Optional.empty();
        }

        return create(() -> {
            Label label = next();
            label.setType(type);
            label.setName(name);
            return label;
        });
    }

    @Override
    protected <S extends Label> Long getEntityId(S entity) {
        return entity.getId();
    }
}
