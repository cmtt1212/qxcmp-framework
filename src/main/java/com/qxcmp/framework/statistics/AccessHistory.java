package com.qxcmp.framework.statistics;

import com.qxcmp.framework.web.view.annotation.table.EntityTable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 访问记录实体
 *
 * @author Aaric
 */
@Entity
@Table
@Data
public class AccessHistory {

    @Id
    @GeneratedValue
    private Long id;

    private Date dateCreated;

    private String ip;

    private String url;

    private String userId;
}
