package com.qxcmp.framework.domain;

import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewAction;
import com.qxcmp.framework.view.annotation.TableViewField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 兑换码实体
 *
 * @author aaric
 */
@Entity
@Table
@Data
@TableView(caption = "兑换码列表", actionUrlPrefix = QXCMP_BACKEND_URL + "/redeem/list/",
        createAction = @TableViewAction(disabled = true),
        removeAction = @TableViewAction(disabled = true),
        updateAction = @TableViewAction(disabled = true))
public class RedeemKey {

    /**
     * 主键，也为兑换码的值，有平台自动生成
     */
    @Id
    private String id;

    /**
     * 使用兑换码的用户ID
     */
    private String userId;

    /**
     * 兑换码类型，用于具体业务逻辑处理
     */
    @TableViewField(title = "业务类型")
    private String type;

    /**
     * 兑换码内容，业务处理需要用到的数据
     */
    private String content;

    /**
     * 兑换码状态
     */
    @TableViewField(title = "使用状态", fieldSuffix = ".value")
    private RedeemKeyStatus status;

    /**
     * 兑换码创建时间
     */
    @TableViewField(title = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateCreated;

    /**
     * 兑换码过期时间
     */
    @TableViewField(title = "过期时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateExpired;
}
