package com.qxcmp.framework.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewAction;
import com.qxcmp.framework.view.annotation.TableViewField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 平台文章实体
 *
 * @author aaric
 */
@Entity
@Table
@Data
@TableView(name = "draft", caption = "我的草稿", actionUrlPrefix = QXCMP_BACKEND_URL + "/article/", createAction = @TableViewAction(disabled = true), findAction = @TableViewAction(title = "预览"), customActions = @TableViewAction(title = "申请发布", urlSuffix = "/auditing", isForm = true))
@TableView(name = "auditing", caption = "待审核文章", actionUrlPrefix = QXCMP_BACKEND_URL + "/article/", createAction = @TableViewAction(disabled = true), findAction = @TableViewAction(title = "预览"))
@TableView(name = "reject", caption = "未通过文章", actionUrlPrefix = QXCMP_BACKEND_URL + "/article/", createAction = @TableViewAction(disabled = true), findAction = @TableViewAction(title = "预览"), customActions = @TableViewAction(title = "申请发布", urlSuffix = "/auditing", isForm = true))
@TableView(name = "published", caption = "已发布文章", actionUrlPrefix = QXCMP_BACKEND_URL + "/article/", createAction = @TableViewAction(disabled = true), findAction = @TableViewAction(title = "预览"), updateAction = @TableViewAction(disabled = true), removeAction = @TableViewAction(disabled = true))
@TableView(name = "disabled", caption = "被禁用文章", actionUrlPrefix = QXCMP_BACKEND_URL + "/article/", createAction = @TableViewAction(disabled = true), findAction = @TableViewAction(title = "预览"))
@TableView(name = "audit", caption = "审核文章", actionUrlPrefix = QXCMP_BACKEND_URL + "/article/", createAction = @TableViewAction(disabled = true), findAction = @TableViewAction(disabled = true), updateAction = @TableViewAction(title = "审核", urlSuffix = "/audit"), removeAction = @TableViewAction(disabled = true))
@TableView(name = "publish", caption = "已发布文章", actionUrlPrefix = QXCMP_BACKEND_URL + "/article/", createAction = @TableViewAction(disabled = true), findAction = @TableViewAction(disabled = true), updateAction = @TableViewAction(disabled = true), removeAction = @TableViewAction(disabled = true), customActions = @TableViewAction(title = "禁用", urlSuffix = "/disable", isForm = true))
@TableView(name = "disable", caption = "已禁用文章", actionUrlPrefix = QXCMP_BACKEND_URL + "/article/", createAction = @TableViewAction(disabled = true), findAction = @TableViewAction(disabled = true), updateAction = @TableViewAction(disabled = true), removeAction = @TableViewAction(disabled = true), customActions = @TableViewAction(title = "启用", urlSuffix = "/enable", isForm = true))
public class Article {

    /**
     * 主键，由框架自动生成
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 文章创建者用户ID
     */
    private String userId;

    /**
     * 文章标题
     */
    @TableViewField(title = "标题")
    private String title;

    /**
     * 文章作者
     */
    @TableViewField(name = "audit", title = "作者")
    private String author;

    /**
     * 文章摘要
     */
    private String digest;

    /**
     * 文章封面图片Url
     */
    @TableViewField(title = "ID", order = Integer.MIN_VALUE, isImage = true)
    private String cover;

    /**
     * 编辑器内容，用于编辑时使用
     */
    @Lob
    private String quillContent;

    /**
     * HTML内容，用于只读时使用
     */
    @Lob
    private String htmlContent;

    /**
     * 文章是否被发布
     */
    @Enumerated
    private ArticleStatus status;

    /**
     * 文章审核人
     */
    @TableViewField(name = "reject", title = "审核人")
    @TableViewField(name = "published", title = "审核人")
    @TableViewField(name = "disabled", title = "禁用人")
    private String auditor;

    /**
     * 文章申请审核说明
     */
    private String auditRequest;

    /**
     * 文章申请审核响应，如通过审核，说明通过原因，如驳回审核，说明驳回原因
     */
    @TableViewField(name = "reject", title = "驳回理由")
    private String auditResponse;

    /**
     * 文章创建时间
     */
    private Date dateCreated;

    /**
     * 上次修改时间
     */
    @TableViewField(title = "修改日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date dateModified;

    /**
     * 文章发布时间
     */
    private Date datePublished;

    /**
     * 文章所属的栏目
     */
    @ManyToMany
    @TableViewField(name = "audit", title = "所属栏目", isCollection = true, collectionEntityIndex = "name")
    private List<Channel> channels = Lists.newArrayList();

    /**
     * 文章访问历史
     */
    @ElementCollection
    private Set<String> viewRecord = Sets.newHashSet();

    /**
     * 文章流量
     */
    private int viewCount;
}
