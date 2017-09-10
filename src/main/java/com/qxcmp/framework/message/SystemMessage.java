package com.qxcmp.framework.message;


import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewField;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 用户站内信信箱
 *
 * @author Tony
 */
@Entity
@Table
@Data
@TableView(caption = "Inbox")
public class SystemMessage {
    @Id
    @GeneratedValue
    private Long id;
    private String userId;
    @TableViewField(title = "标题")
    private String title;
    @TableViewField(title = "内容")
    private String content;
    @TableViewField(title = "发送时间")
    private Date date;
    @TableViewField(title = "已读")
    private boolean is_read;
}
