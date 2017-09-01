package com.qxcmp.framework.web.view.elements.html;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 段落
 *
 * @author aaric
 */
@Getter
@Setter
public class Paragraph extends BlockElement {
    public Paragraph() {
        super("p");
    }
}
