package com.qxcmp.framework.web.view;

import com.google.common.collect.Lists;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

public abstract class AbstractPage {

    private static final String DEFAULT_PAGE_VIEW = "qxcmp";

    private ModelAndView modelAndView = new ModelAndView(DEFAULT_PAGE_VIEW);

    private HttpServletRequest request;

    private HttpServletResponse response;

    private List<Component> components = Lists.newArrayList();

    public AbstractPage(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public AbstractPage setTitle(String title) {
        modelAndView.addObject("title", title);
        return this;
    }

    public AbstractPage addComponent(Component component) {
        components.add(component);
        return this;
    }

    public AbstractPage addComponents(Collection<? extends Component> components) {
        this.components.addAll(components);
        return this;
    }

    public AbstractPage addObject(Object object) {
        modelAndView.addObject(object);
        return this;
    }

    public AbstractPage addObject(String key, Object object) {
        modelAndView.addObject(key, object);
        return this;
    }

    public ModelAndView build() {
        modelAndView.addObject("page", this);
        return modelAndView;
    }

    public List<Component> getComponents() {
        return components;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
