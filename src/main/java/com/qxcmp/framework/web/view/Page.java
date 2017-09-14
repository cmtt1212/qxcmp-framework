package com.qxcmp.framework.web.view;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.support.utils.FormHelper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

/**
 * 页面对象
 * <p>
 * 包含了渲染页面所有的信息
 *
 * @author aaric
 */
@org.springframework.stereotype.Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Page {

    private static final String DEFAULT_PAGE_VIEW = "qxcmp";

    private HttpServletRequest request;

    private HttpServletResponse response;

    private ModelAndView modelAndView = new ModelAndView(DEFAULT_PAGE_VIEW);

    public Page(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 页面组件，框架会依次渲染各个组件
     */
    @Getter
    private List<Component> components = Lists.newArrayList();

    public Page addComponent(Component component) {
        components.add(component);
        return this;
    }

    public Page addComponents(Collection<? extends Component> components) {
        this.components.addAll(components);
        return this;
    }

    public Page addObject(Object object) {
        modelAndView.addObject(object);
        return this;
    }

    public Page addObject(String key, Object object) {
        modelAndView.addObject(key, object);
        return this;
    }

    public ModelAndView build() {
        modelAndView.addObject(this);
        return modelAndView;
    }
}
