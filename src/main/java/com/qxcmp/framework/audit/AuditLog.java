package com.qxcmp.framework.audit;

import com.qxcmp.framework.user.User;
import com.qxcmp.framework.view.annotation.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 审计日志实体
 * <p>
 * 用于记录用户操作或者系统特定操作，审计日志需要包含When，Where，How，Who，What这些信息
 * <p>
 * 审计日志方便后期对系统故障的调试和查错
 *
 * @author aaric
 */
@Entity
@Table
@TableView(caption = "审计日志",
        actionUrlPrefix = QXCMP_BACKEND_URL + "/log/audit/",
        createAction = @TableViewAction(disabled = true),
        updateAction = @TableViewAction(disabled = true),
        removeAction = @TableViewAction(disabled = true))
@DictionaryView(title = "审计日志")
@Data
public class AuditLog {

    /**
     * 主键，由框架自动生成
     */
    @Id
    @GeneratedValue
    @DictionaryViewField("ID")
    private Long id;

    /**
     * 日志时间
     */
    @TableViewField(title = "时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DictionaryViewField("操作时间")
    private Date dateCreated;

    /**
     * 日志请求链接
     */
    private String url;

    /**
     * 日志名称
     */
    @TableViewField(title = "名称")
    private String title;

    /**
     * 日志当事人
     */
    @TableViewField(title = "当事人", fieldSuffix = ".nickname")
    @ManyToOne
    private User owner;

    /**
     * 日志内容
     */
    @Lob
    private String content;

    /**
     * 备注信息
     */
    @Lob
    private String comments;

    /**
     * 结果状态
     */
    @TableViewField(title = "结果")
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * 操作上下文
     */
    @Transient
    private Map<String, Object> actionContext;

    /**
     * 结果状态枚举
     */
    public enum Status {

        SUCCESS("success"),
        FAILURE("failure");

        private String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
