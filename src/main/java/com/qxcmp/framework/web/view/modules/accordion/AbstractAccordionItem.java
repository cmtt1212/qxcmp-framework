package com.qxcmp.framework.web.view.modules.accordion;

import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.Component;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractAccordionItem extends AbstractComponent {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private Component content;

    /**
     * 是否为激活状态
     */
    private boolean active;

    @Override
    public String getFragmentFile() {
        return "qxcmp/modules/accordion";
    }

    public AbstractAccordionItem setActive() {
        setActive(true);
        return this;
    }

    public AbstractAccordionItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public AbstractAccordionItem setContent(Component content) {
        this.content = content;
        return this;
    }
}
