package com.qxcmp.core.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 实体服务抽象接口，该接口的实现支持以下服务
 * <p>
 * 实体对象的增删改查操作 实体对象的排序和分页查询 实体对象的条件查询
 *
 * @param <T>  模块实体类型
 * @param <ID> 模块实体主键类型
 * @author aaric
 */
public interface EntityService<T, ID extends Serializable> {

    /**
     * 检查实体是否存在
     *
     * @param id 实体主键
     * @return 该主键对应实体是否存在
     */
    Boolean exist(ID id);

    /**
     * 返回实体总数
     *
     * @return 实体总数
     */
    Long count();

    /**
     * 返回符合查询条件的实体总数
     *
     * @param specification 查询条件
     * @return 符合查询条件的实体个数
     */
    Long count(Specification<T> specification);

    /**
     * 获取实体对象所在类的信息
     *
     * @return 获取实体对象所在类的信息
     */
    Class<T> type();

    /**
     * 实体工厂方法，用于创建一个新的实体对象 实体对象必须拥有默认构造函数
     *
     * @return 新的实体对象
     */
    T next();

    /**
     * 创建一个实体对象，若对象已存在，则返回空
     *
     * @param supplier 实体对象提供函数
     * @param <S>      实体对象类型
     * @return 创建以后的实体对象
     */
    <S extends T> Optional<S> create(Supplier<S> supplier);

    /**
     * 更新一个实体对象，如果对象不存在，则返回空 更新对象不能修改对象的ID
     *
     * @param id      要更新的实体主键
     * @param present 实体更新操作函数
     * @param <S>     实体对象类型
     * @return 更新以后的实体对象
     */
    <S extends T> Optional<S> update(ID id, Consumer<S> present);

    /**
     * 创建或者更新一个实体对象
     *
     * @param entity 要创建或者更新的实体对象
     * @param <S>    实体对象类型
     * @return 创建或者修改以后的实体对象
     */
    <S extends T> S save(S entity);

    /**
     * 通过实体主键删除实体对象
     *
     * @param id 要删除的实体主键
     */
    void remove(ID id);

    /**
     * 通过实体本身删除实体对象
     *
     * @param entity 要删除的实体对象
     */
    void remove(T entity);

    /**
     * 删除所有实体对象
     */
    void removeAll();

    /**
     * 通过实体主键查找实体对象
     *
     * @param id 要查询的实体主键
     * @return {@code Optional.empty()} 如果主键不存在，否则返回该对象的{@link Optional}包装
     */
    Optional<T> findOne(ID id);

    /**
     * 返回第一个匹配查询条件的实体对象，如果没有找到则返回空
     *
     * @param specification 查询条件
     * @return 第一个符合查询条件的实体对象
     */
    Optional<T> findOne(Specification<T> specification);

    /**
     * 查询所有实体对象
     *
     * @return 所有实体对象
     */
    List<T> findAll();

    /**
     * 排序查找所有实体对象
     *
     * @param sort 排序参数
     * @return 排序后的实体对象
     */
    List<T> findAll(Sort sort);

    /**
     * 分页查询所有实体对象
     *
     * @param pageable 分页查询参数
     * @return 分页查询后的实体对象
     */
    Page<T> findAll(Pageable pageable);

    /**
     * 返回符合条件的实体对象
     *
     * @param specification 查询条件
     * @return 符合查询条件的所有实体对象
     */
    List<T> findAll(Specification<T> specification);

    /**
     * 返回符合条件的实体对象并排序
     *
     * @param specification 查询条件
     * @param sort          排序参数
     * @return 符合查询条件并且排序后的实体对象
     */
    List<T> findAll(Specification<T> specification, Sort sort);

    /**
     * 分页查询符合条件的实体对象
     *
     * @param specification 查询条件
     * @param pageable      分页查询参数
     * @return 符合查询条件并且分页后的结果
     */
    Page<T> findAll(Specification<T> specification, Pageable pageable);

    /**
     * 模糊搜索实体对象
     *
     * @param content  搜索内容
     * @param fields   搜索匹配的实体字段
     * @param pageable 分页信息
     * @return 搜索结果分页内容
     */
    Page<T> search(String content, List<Field> fields, Pageable pageable);
}
