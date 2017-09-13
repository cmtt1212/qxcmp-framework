package com.qxcmp.framework.web.view;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

/**
 * 页面对象
 * <p>
 * 包含了渲染页面所有的信息
 *
 * @author aaric
 */
@Getter
@Setter
public class Page {

    private static final String DEFAULT_PAGE_VIEW = "qxcmp";

    private ModelAndView modelAndView = new ModelAndView(DEFAULT_PAGE_VIEW);

    public Page() {
        this(DEFAULT_PAGE_VIEW);
    }

    public Page(String viewName) {
        modelAndView = new ModelAndView(viewName);
    }

    /**
     * 页面组件，框架会依次渲染各个组件
     */
    private List<Component> components = Lists.newArrayList();

    public Page addComponent(Component component) {
        components.add(component);
        return this;
    }

    public Page addComponents(Collection<? extends Component> components) {
        this.components.addAll(components);
        return this;
    }

    public ModelAndView build() {
        modelAndView.addObject(this);
        return modelAndView;
    }
}
