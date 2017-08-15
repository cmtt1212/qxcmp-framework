package com.qxcmp.framework.spdier.log;

import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 蜘蛛日志实体
 * <p>
 * 记录了一次蜘蛛抓取的相信信息
 *
 * @author aaric
 */
@Entity
@Table
@TableView(caption = "蜘蛛日志", disableAction = true, actionUrlPrefix = QXCMP_BACKEND_URL + "/spider/log/")
@Data
public class SpiderLog {

    /**
     * 主键，由框架自动生成
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 蜘蛛所属组
     */
    @TableViewField(title = "蜘蛛组")
    private String spiderGroup;

    /**
     * 蜘蛛名称
     */
    @TableViewField(title = "名称")
    private String name;

    /**
     * 抓取内容页面
     */
    @TableViewField(title = "抓取内容页面")
    private Long contentPageCount;

    /**
     * 抓取的目标页面
     */
    @TableViewField(title = "抓取目标页面")
    private Long targetPageCount;

    /**
     * 新添实体数量
     */
    @TableViewField(title = "新添数量")
    private Long newPageCount;

    /**
     * 更新实体数量
     */
    @TableViewField(title = "更新数量")
    private Long updatePageCount;

    /**
     * 丢弃实体数量
     */
    @TableViewField(title = "丢弃数量")
    private Long dropPageCount;

    /**
     * 蜘蛛耗时
     */
    @TableViewField(title = "抓取时长")
    private String duration;

    /**
     * 抓取开始时间
     */
    @TableViewField(title = "开始时间")
    @DateTimeFormat(pattern = "yyy-MM-dd HH:mm")
    private Date dateStart;

    /**
     * 抓取结束时间
     */
    @TableViewField(title = "结束时间")
    @DateTimeFormat(pattern = "yyy-MM-dd HH:mm")
    private Date dateFinish = new Date();
}
