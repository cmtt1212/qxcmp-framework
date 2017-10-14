package com.qxcmp.framework.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 地区实体
 * <p>
 * 代表一个行政区或者地区
 *
 * @author Aaric
 */
@Entity
@Table
@Data
public class Region {

    /**
     * 地区代码
     */
    @Id
    private String code;

    /**
     * 上级地区代码
     */
    private String parent;

    /**
     * 地区级别
     */
    @Enumerated
    private RegionLevel level;

    /**
     * 地区名称
     */
    private String name;

    /**
     * 是否禁用
     * <p>
     * 禁用以后将无法获取本地区以及所有下级地区
     */
    private boolean disable;
}
