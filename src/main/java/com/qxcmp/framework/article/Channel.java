package com.qxcmp.framework.article;

import com.google.common.collect.Sets;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewAction;
import com.qxcmp.framework.view.annotation.TableViewField;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 文章栏目实体
 *
 * @author aaric
 */
@Entity
@Table
@Data
@TableView(caption = "栏目列表", actionUrlPrefix = QXCMP_BACKEND_URL + "/article/channel/",
        findAction = @TableViewAction(title = "预览", urlSuffix = "/preview"),
        removeAction = @TableViewAction(disabled = true))
public class Channel {

    /**
     * 主键，由平台自动生成
     */
    @Id
    @GeneratedValue
    @TableViewField(title = "ID")
    private Long id;

    /**
     * 栏目显示名称
     */
    @TableViewField(title = "名称")
    private String name;

    /**
     * 栏目别名，用户访问栏目使用
     */
    @Column(unique = true)
    private String alias;

    @TableViewField(title = "封面", order = Integer.MIN_VALUE, isImage = true)
    private String cover;

    /**
     * 栏目描述
     */
    private String description;

    /**
     * 栏目自定义页面
     */
    @Lob
    private String quillContent;

    /**
     * 栏目自定义页面
     */
    @Lob
    private String htmlContent;

    /**
     * 栏目所有者
     */
    @ManyToOne
    @TableViewField(title = "所有者", fieldSuffix = ".username")
    private User owner;

    /**
     * 栏目管理者
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> admins = Sets.newHashSet();

    /**
     * 栏目类型
     */
    @ElementCollection
    private Set<String> catalog = Sets.newHashSet();
}
