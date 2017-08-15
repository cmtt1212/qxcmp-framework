package com.qxcmp.framework.spdier;

import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewAction;
import com.qxcmp.framework.view.annotation.TableViewField;
import us.codecraft.webmagic.Spider;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 蜘蛛运行时信息
 * <p>
 * 存放了一个正在运行的蜘蛛信息
 *
 * @author aaric
 */
@TableView(
        caption = "蜘蛛状态列表",
        actionUrlPrefix = QXCMP_BACKEND_URL + "/spider/status/",
        entityIndex = "name",
        removeAction = @TableViewAction(disabled = true),
        createAction = @TableViewAction(disabled = true),
        findAction = @TableViewAction(disabled = true),
        updateAction = @TableViewAction(disabled = true),
        customActions = @TableViewAction(title = "停止", isForm = true)
)
public class SpiderRuntime {

    @TableViewField(title = "蜘蛛组")
    private String group;

    @TableViewField(title = "蜘蛛名称")
    private String name;

    private us.codecraft.webmagic.Spider spider;

    private SpiderPageProcessor spiderPageProcessor;

    public SpiderRuntime(String group, String name, Spider spider, SpiderPageProcessor spiderPageProcessor) {
        this.group = group;
        this.name = name;
        this.spider = spider;
        this.spiderPageProcessor = spiderPageProcessor;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Spider getSpider() {
        return spider;
    }

    public void setSpider(Spider spider) {
        this.spider = spider;
    }

    public SpiderPageProcessor getSpiderPageProcessor() {
        return spiderPageProcessor;
    }

    public void setSpiderPageProcessor(SpiderPageProcessor spiderPageProcessor) {
        this.spiderPageProcessor = spiderPageProcessor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpiderRuntime that = (SpiderRuntime) o;

        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = group != null ? group.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
