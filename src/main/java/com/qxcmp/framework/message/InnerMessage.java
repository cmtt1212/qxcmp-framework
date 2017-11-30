package com.qxcmp.framework.message;


import com.qxcmp.framework.web.view.annotation.table.EntityTable;
import com.qxcmp.framework.web.view.annotation.table.RowAction;
import com.qxcmp.framework.web.view.annotation.table.TableField;
import com.qxcmp.framework.web.view.modules.form.FormMethod;
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
@EntityTable(value = "我的站内消息", action = QXCMP_BACKEND_URL + "/profile/message", disableFilter = true,
        rowActions = {
                @RowAction(value = "查看", action = "details"),
                @RowAction(value = "标位已读", action = "read", method = FormMethod.POST),
                @RowAction(value = "删除", action = "remove", method = FormMethod.POST)
        })
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
