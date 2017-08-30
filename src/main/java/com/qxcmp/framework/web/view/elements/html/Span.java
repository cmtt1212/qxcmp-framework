package com.qxcmp.framework.web.view.elements.html;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 内联文档节
 *
 * @author aaric
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Span extends InlineElement{
    public Span() {
        super("span");
    }
}
