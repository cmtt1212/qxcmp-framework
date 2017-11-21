package com.qxcmp.framework.statistics;

import com.qxcmp.framework.web.view.annotation.table.EntityTable;
import com.qxcmp.framework.web.view.annotation.table.TableField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 访问地址实体
 * <p>
 * 该实体可用于定义IP黑白名单
 * <p>
 * 用户可以通过后台管理访问地址
 * <p>
 * 第三方业务可以调用相应接口进行业务逻辑处理
 *
 * @author Aaric
 */
@EntityTable("访问地址管理")
@Entity
@Table
@Data
public class AccessAddress {

    @Id
    @TableField("地址")
    private String address;

    @TableField("类型")
    private AccessAddressType type;

    @TableField("备注")
    @Size(max = 20)
    private String comments;

    @TableField("最近访问时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
}
