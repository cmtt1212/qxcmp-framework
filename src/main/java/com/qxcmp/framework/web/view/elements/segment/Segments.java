package com.qxcmp.framework.web.view.elements.segment;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 片段组
 */
@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Segments extends AbstractComponent implements Segmentable {

    /**
     * 是否为紧凑模式
     * <p>
     * 该模式的片段宽度为内容实际宽度
     */
    private boolean compact;

    /**
     * 片段组内容
     */
    private List<Segmentable> items = Lists.newArrayList();

    public Segments() {
        super("qxcmp/containers/segment");
    }

    @Override
    public String getFragmentName() {
        return "segments";
    }
}
