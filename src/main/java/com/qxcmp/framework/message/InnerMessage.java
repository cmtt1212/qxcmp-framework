package com.qxcmp.framework.message;


import com.qxcmp.framework.web.view.annotation.table.EntityTable;
import com.qxcmp.framework.web.view.annotation.table.TableAction;
import com.qxcmp.framework.web.view.annotation.table.TableField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static com.qxcmp.framework.core.QxcmpConfiguration.QXCMP_BACKEND_URL;

/**
 * 站内消息
 *
 * @author Aaric
 */
@EntityTable(value = "站内消息", name = "admin", action = QXCMP_BACKEND_URL + "/inbox",
        tableActions = @TableAction(value = "发信息", action = "new", primary = true))
@Entity
@Table
@Data
public class InnerMessage {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private String sender;

    @TableField("标题")
    private String title;

    private String content;

    @TableField("发送时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    private boolean unread;
}
