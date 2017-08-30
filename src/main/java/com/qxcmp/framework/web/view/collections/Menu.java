package com.qxcmp.framework.web.view.collections;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Menu extends AbstractMenu {

    private boolean text;

    @Singular
    private List<MenuItem> items;

}
