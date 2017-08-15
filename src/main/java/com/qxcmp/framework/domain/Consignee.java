package com.qxcmp.framework.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 收货人实体
 *
 * @author aaric
 */
@Entity
@Table
@Data
public class Consignee {

    /**
     * 主键，有平台生成
     */
    @Id
    private String id;

    /**
     * 收货人用户ID
     */
    private String userId;

    /**
     * 显示名称，用于快速识别
     * <p>
     * 比如：家里、公司、父母家等等
     */
    private String name;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 联系邮箱
     */
    private String email;

    /**
     * 创建日期
     */
    private Date dateCreated;

    /**
     * 修改日期
     */
    private Date dateModified;
}
