package com.qxcmp.framework.news;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.qxcmp.framework.view.annotation.TableViewField;
import com.qxcmp.framework.web.view.annotation.table.EntityTable;
import com.qxcmp.framework.web.view.annotation.table.TableField;
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
@EntityTable(value = "我的文章", name = "user", action = QXCMP_BACKEND_URL + "/news/article")
@EntityTable(value = "草稿箱", name = "userDraft", action = QXCMP_BACKEND_URL + "/news/article")
@EntityTable(value = "审核中文章", name = "userAuditing", action = QXCMP_BACKEND_URL + "/news/article")
@EntityTable(value = "未通过文章", name = "userRejected", action = QXCMP_BACKEND_URL + "/news/article")
@EntityTable(value = "已发布文章", name = "userPublished", action = QXCMP_BACKEND_URL + "/news/article")
@EntityTable(value = "已禁用文章", name = "userDisabled", action = QXCMP_BACKEND_URL + "/news/article")
@EntityTable(value = "待审核文章", name = "auditing", action = QXCMP_BACKEND_URL + "/news/article")
@EntityTable(value = "已发布文章", name = "published", action = QXCMP_BACKEND_URL + "/news/article")
@EntityTable(value = "已禁用文章", name = "disabled", action = QXCMP_BACKEND_URL + "/news/article")
@Entity
@Table
@Data
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
    @TableField("标题")
    private String title;

    /**
     * 文章作者
     */
    @TableField(value = "作者", name = "auditing", fieldSuffix = ".username")
    @TableField(value = "作者", name = "published", fieldSuffix = ".username")
    @TableField(value = "作者", name = "disabled", fieldSuffix = ".username")
    private String author;

    /**
     * 文章摘要
     */
    private String digest;

    /**
     * 文章封面图片Url
     */
    @TableField(value = "封面", image = true, order = Integer.MIN_VALUE)
    private String cover;

    /**
     * 编辑器内容，用于编辑时使用
     */
    @Lob
    private String content;

    /**
     * HTML内容，用于只读时使用
     */
    @Lob
    private String contentQuill;

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
