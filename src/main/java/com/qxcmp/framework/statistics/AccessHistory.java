package com.qxcmp.framework.statistics;

import com.qxcmp.framework.web.view.annotation.table.EntityTable;
import com.qxcmp.framework.web.view.annotation.table.TableField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 访问记录实体
 *
 * @author Aaric
 */
@EntityTable(value = "访问记录", action = QXCMP_BACKEND_URL + "/statistics/access/history")
@Entity
@Table
@Data
public class AccessHistory {

    @Id
    @GeneratedValue
    private Long id;

    @TableField("访问日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateCreated;

    @TableField("访问地址")
    private String ip;

    @TableField("访问链接")
    private String url;

    private String userId;
}
