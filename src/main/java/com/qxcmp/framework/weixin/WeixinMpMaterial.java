package com.qxcmp.framework.weixin;

import com.qxcmp.framework.web.view.annotation.table.*;
import com.qxcmp.framework.web.view.modules.form.FormMethod;
import com.qxcmp.framework.web.view.modules.table.TableData;
import com.qxcmp.framework.web.view.support.Alignment;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 微信素材实体
 *
 * @author Aaric
 */
@EntityTable(value = "公众号素材管理", action = QXCMP_BACKEND_URL + "/weixin/material", disableFilter = true,
        tableActions = @TableAction(value = "同步素材", action = "sync", method = FormMethod.POST, primary = true),
        rowActions = @RowAction(value = "查看", action = "preview"))
@Entity
@Table
@Data
public class WeixinMpMaterial {

    /**
     * 素材主键
     * <p>
     * 图文素材为图文素材MediaId加上游标
     * <p>
     * 其他素材为图文素材MediaId
     */
    @Id
    private String id;

    /**
     * 素材类型
     */
    @TableField("素材类型")
    private WeixinMpMaterialType type;

    /**
     * 素材名称
     * <p>
     * 图文素材为图文标题
     * <p>
     * 其他素材为素材名称
     */
    @TableField("素材名称")
    private String title;

    private String thumbMediaId;

    @TableField(value = "封面", image = true, order = Integer.MIN_VALUE)
    private String thumbUrl;

    private boolean showCover;

    private String author;

    private String digest;

    @Lob
    private String content;

    /**
     * 图文Url或者图片Url
     */
    private String url;

    private String sourceUrl;

    @TableFieldRender("type")
    public TableData renderTypeField() {
        final TableData tableData = new TableData();

        switch (type) {
            case NEWS:
                tableData.setContent("图文");
                break;
            case IMAGE:
                tableData.setContent("图片");
                break;
            case VOICE:
                tableData.setContent("语音");
                break;
            case VIDEO:
                tableData.setContent("视频");
                break;
            default:
                tableData.setContent("未知");
        }

        tableData.setAlignment(Alignment.CENTER);
        return tableData;
    }

    @RowActionCheck("查看")
    public boolean canPerformPreviewAction() {
        return type.equals(WeixinMpMaterialType.NEWS);
    }
}
