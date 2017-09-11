package com.qxcmp.framework.web.view.elements.menu.item;

import com.qxcmp.framework.web.view.modules.accordion.AccordionItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccordionMenuItem extends AbstractMenuItem {

    private AccordionItem item;

    public AccordionMenuItem(AccordionItem item) {
        this.item = item;
    }
}
