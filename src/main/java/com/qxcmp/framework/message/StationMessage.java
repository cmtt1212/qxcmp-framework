package com.qxcmp.framework.message;


import com.qxcmp.framework.web.view.annotation.table.EntityTable;
import com.qxcmp.framework.web.view.annotation.table.TableAction;
import com.qxcmp.framework.web.view.annotation.table.TableField;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@EntityTable(value = "我的信箱", name = "inbox", action = QXCMP_BACKEND_URL + "/inbox",
        tableActions = @TableAction(value = "发信息", action = "new", primary = true))
@Entity
@Table
@Data
public class StationMessage {

    @Id
    @GeneratedValue
    private Long id;

    private String userID;

    @TableField("发件人")
    private String sender;

    @TableField("内容")
    private String content;

    @TableField("发送时间")
    private Date sendTime;

    @TableField("已读")
    private boolean alreadyRead;
}
