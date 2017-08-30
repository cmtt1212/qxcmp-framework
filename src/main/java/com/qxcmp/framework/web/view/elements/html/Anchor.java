package com.qxcmp.framework.web.view.elements.html;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 超链接
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Anchor extends InlineElement {

    /**
     * 超链接地址
     */
    private String href;

    /**
     * 超链接打开方式
     *
     * @see com.qxcmp.framework.web.view.support.AnchorTarget
     */
    private String target;

    /**
     * 若超链接表示下载文件，则该属性用来设置要下载的文件名称，浏览器将自动检测文件扩展名
     */
    private String download;

    public Anchor() {
        super("anchor");
    }


}
