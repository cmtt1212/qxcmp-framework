package com.qxcmp.framework.core.entity;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * 实体服务抽象实现
 *
 * @author aaric
 * @see EntityService
 */
public abstract class AbstractEntityService<T, ID extends Serializable, R extends JpaRepository<T, ID> & JpaSpecificationExecutor<T>> implements EntityService<T, ID> {

    protected R repository;

    private TypeToken<T> typeToken = new TypeToken<T>(getClass()) {
    };

    public AbstractEntityService(R repository) {
        this.repository = repository;
    }

    @Override
    public Boolean exist(ID id) {
        return repository.exists(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Long count(Specification<T> specification) {
        return repository.count(specification);
    }

    /**
     * 获取实体对象所在类的信息
     *
     * @return 获取实体对象所在类的信息
     */
    @Override
    @SuppressWarnings("unchecked")
    public Class<T> type() {
        return (Class<T>) typeToken.getRawType();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T next() {
        try {
            return (T) typeToken.getRawType().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public <S extends T> Optional<S> create(Supplier<S> supplier) {
        S entity = supplier.get();
        checkNotNull(entity, "Entity can't be null");

        if (findOne(getEntityId(entity)).isPresent()) {
            return Optional.empty();
        } else {
            return Optional.of(repository.save(entity));
        }
    }

    @Override
    public <S extends T> Optional<S> update(ID id, Consumer<S> present) {
        checkNotNull(id, "Entity ID can't be null");

        @SuppressWarnings("unchecked")
        Optional<S> entity = (Optional<S>) findOne(id);

        if (entity.isPresent()) {

            present.accept(entity.get());

            checkState(getEntityId(entity.get()).equals(id), "Can't change entity ID while update entity");

            return entity.map(s -> repository.save(s));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public <S extends T> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public void remove(ID id) {
        repository.delete(id);
    }

    @Override
    public void remove(T entity) {
        repository.delete(entity);
    }

    @Override
    public void removeAll() {
        repository.deleteAll();
    }

    @Override
    public Optional<T> findOne(ID id) {
        return Objects.isNull(id) ? Optional.empty() : Optional.ofNullable(repository.findOne(id));
    }

    @Override
    public Optional<T> findOne(Specification<T> specification) {
        return Optional.ofNullable(repository.findOne(specification));
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<T> findAll(Specification<T> specification) {
        return repository.findAll(specification);
    }

    @Override
    public List<T> findAll(Specification<T> specification, Sort sort) {
        return repository.findAll(specification, sort);
    }

    @Override
    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public Page<T> search(String content, List<Field> fields, Pageable pageable) {
        return StringUtils.isBlank(content) ? findAll(pageable) : findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> restrictions = Lists.newArrayList();

            fields.forEach(field -> {
                if (field.getType().equals(String.class)) {
                    restrictions.add(criteriaBuilder.like(root.get(field.getName()), String.format("%%%s%%", content.toLowerCase())));
                }
            });

            return criteriaBuilder.or(restrictions.toArray(new Predicate[restrictions.size()]));
        }, pageable);
    }

    /**
     * 从实体对象获取实体主键的方式，子类唯一需要实现的接口 该方法不能返回{null}值
     *
     * @param entity 实体对象
     * @param <S>    实体对象类型或者子类型
     *
     * @return 实体对象的主键
     */
    protected abstract <S extends T> ID getEntityId(S entity);
}
