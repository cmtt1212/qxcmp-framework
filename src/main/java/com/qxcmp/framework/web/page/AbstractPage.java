package com.qxcmp.framework.web.page;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.html.JavaScript;
import com.qxcmp.framework.web.view.html.Stylesheet;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

public abstract class AbstractPage {

    private static final String DEFAULT_PAGE_VIEW = "qxcmp";
    private static final String HTML_STYLESHEET = "stylesheet";
    private static final String HTML_JAVASCRIPT = "javascript";
    private static final String HTML_JAVASCRIPT_BODY = "javascriptBody";

    private ModelAndView modelAndView = new ModelAndView(DEFAULT_PAGE_VIEW);

    private HttpServletRequest request;

    private HttpServletResponse response;

    private List<Component> components = Lists.newArrayList();

    private List<Stylesheet> stylesheets = Lists.newArrayList();

    private List<JavaScript> javaScripts = Lists.newArrayList();

    private List<JavaScript> bodyJavaScripts = Lists.newArrayList();

    public AbstractPage(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public AbstractPage setTitle(String title) {
        modelAndView.addObject("title", title);
        return this;
    }

    public AbstractPage setViewName(String viewName) {
        modelAndView.setViewName(viewName);
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

    public AbstractPage addStylesheet(Stylesheet stylesheet) {
        stylesheets.add(stylesheet);
        return this;
    }

    public AbstractPage addJavascript(JavaScript javaScript) {
        return addJavascript(javaScript, false);
    }

    public AbstractPage addJavascript(JavaScript javaScript, boolean addToBody) {
        if (addToBody) {
            bodyJavaScripts.add(javaScript);
        } else {
            javaScripts.add(javaScript);
        }
        return this;
    }

    public AbstractPage setBreadcrumb(String... breadcrumb) {
        return this;
    }

    public AbstractPage setVerticalMenu(List<String> menus) {
        return this;
    }

    public AbstractPage setVerticalNavigation(String id, String activeItem) {
        return this;
    }

    public AbstractPage setVerticalNavigationBadge(String id, String text) {
        return this;
    }

    public ModelAndView build() {
        modelAndView.addObject("page", this);
        modelAndView.addObject(HTML_STYLESHEET, stylesheets);
        modelAndView.addObject(HTML_JAVASCRIPT, javaScripts);
        modelAndView.addObject(HTML_JAVASCRIPT_BODY, bodyJavaScripts);
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
