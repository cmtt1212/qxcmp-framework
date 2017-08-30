package com.qxcmp.framework.web.view.elements.html;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 段落
 *
 * @author aaric
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Paragraph extends BlockElement {
    public Paragraph() {
        super("p");
    }
}
