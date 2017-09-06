package com.qxcmp.framework.message.innermessage;


import lombok.Data;

import javax.persistence.Entity;
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
public class InnerMessage {
    @Id
    private String id;
    private String user_id;
    private String title;
    private String content;
    private Date date;
    private Boolean read;
}
