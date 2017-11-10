package com.qxcmp.framework.message;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Aaric
 */
@Entity
@Table
@Data
public class SiteNotification {

    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 发布者ID
     */
    private String ownerId;

    /**
     * 类型
     */
    private String type;

    /**
     * 生效时间
     */
    private Date dateStart;

    /**
     * 结束时间
     */
    private Date dateEnd;

    /**
     * 通知内容
     */
    private String content;

}
