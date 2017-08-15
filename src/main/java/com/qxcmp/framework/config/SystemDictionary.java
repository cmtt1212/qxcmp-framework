package com.qxcmp.framework.config;

import com.google.common.collect.Lists;
import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewAction;
import com.qxcmp.framework.view.annotation.TableViewField;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 系统字典配置
 *
 * @author aaric
 */
@Entity
@Table
@Data
@TableView(caption = "系统字典", actionUrlPrefix = QXCMP_BACKEND_URL + "/site/dictionary/", entityIndex = "name",
        createAction = @TableViewAction(disabled = true),
        removeAction = @TableViewAction(disabled = true))
public class SystemDictionary {

    @Id
    @TableViewField(title = "名称")
    private String name;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @TableViewField(title = "项目", isCollection = true, collectionEntityIndex = "name", urlEnabled = true, urlPrefix = QXCMP_BACKEND_URL + "/site/dictionary/", urlSuffix = "/edit", urlEntityIndex = "parent.name")
    @OrderBy("priority")
    private List<SystemDictionaryItem> items = Lists.newArrayList();
}
