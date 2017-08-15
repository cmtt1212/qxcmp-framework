package com.qxcmp.framework.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 标签实体
 * <p>
 * 标签可以打在每一个实体对象上面，以便从不同维度区分实体
 *
 * @author aaric
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"type", "name"}))
@Data
public class Label {

    /**
     * 主键，由框架自动生成
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 标签类型
     * <p>
     * 用于给标签分组，一个标签类型一般对应一个特定的实体
     */
    private String type;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签创建日期
     */
    private Date dateCreated;

    /**
     * 标签修改日期
     */
    private Date dateModified;

}
