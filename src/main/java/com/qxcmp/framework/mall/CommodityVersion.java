package com.qxcmp.framework.mall;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品版本
 * <p>
 * 可用于定义商品颜色、尺码、种类、版本等分类
 * <p>
 * 结合关联商品使用
 *
 * @author Aaric
 */
@Entity
@Table
@Data
public class CommodityVersion {

    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 版本名称
     */
    private String name;

    /**
     * 版本值
     */
    private String value;
}
