package com.qxcmp.framework.weixin;

import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewAction;
import com.qxcmp.framework.view.annotation.TableViewField;
import lombok.Data;

import javax.persistence.*;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 公众号图文素材实体
 * <p>
 * 用于持久化保存公众号图文素材
 *
 * @author aaric
 */
@Entity
@Table
@Data
@TableView(caption = "图文列表", actionUrlPrefix = QXCMP_BACKEND_URL + "/weixin/material/news/",
        createAction = @TableViewAction(disabled = true),
        updateAction = @TableViewAction(disabled = true),
        removeAction = @TableViewAction(disabled = true),
        customActions = @TableViewAction(title = "发布到网站", urlSuffix = "/publish"))
public class WechatMpNewsArticle {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String url;

    private String thumbMediaId;

    @TableViewField(title = "封面", order = 1, isImage = true)
    private String thumbUrl;

    @TableViewField(title = "作者", order = 3)
    private String author;

    @TableViewField(title = "标题", order = 2)
    private String title;

    private String contentSourceUrl;

    @Lob
    private String content;

    private String digest;

    private boolean showCoverPic;

}
