package com.qxcmp.framework.web.view.elements.html;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 块级文档节
 *
 * @author aaric
 */
@Getter
@Setter
public class Div extends BlockElement {

    public Div() {
        super("div");
    }
}
