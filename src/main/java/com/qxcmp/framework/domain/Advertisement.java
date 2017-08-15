package com.qxcmp.framework.domain;

import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewAction;
import com.qxcmp.framework.view.annotation.TableViewField;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 广告实体
 * <p>
 * 代表了一张广告
 *
 * @author aaric
 */
@TableView(caption = "广告列表", actionUrlPrefix = QXCMP_BACKEND_URL + "/ad/", findAction = @TableViewAction(disabled = true))
@Entity
@Table
@Data
public class Advertisement {

    /**
     * 广告实体主键，由框架自动生成
     */
    @Id
    @GeneratedValue
    @TableViewField(title = "ID")
    private Long id;

    /**
     * 广告类型，用于区分在不同位置显示
     */
    @TableViewField(title = "类型")
    private String type;

    /**
     * 广告名称
     */
    @TableViewField(title = "名称")
    private String title;

    /**
     * 广告图片
     */
    @TableViewField(title = "图片", isImage = true, order = -1)
    private String image;

    /**
     * 广告链接
     */
    @TableViewField(title = "链接")
    private String link;

    /**
     * 广告顺序
     */
    @TableViewField(title = "顺序")
    private int adOrder;
}
