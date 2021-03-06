package com.qxcmp.web.page;

import com.google.common.collect.Lists;
import com.qxcmp.web.view.Component;
import com.qxcmp.web.view.elements.label.AbstractLabel;
import com.qxcmp.web.view.support.Color;
import com.qxcmp.web.view.views.ProfileHeader;
import lombok.Getter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractPage {

    private static final String DEFAULT_PAGE_VIEW = "qxcmp";
    private static final String HTML_PAGE = "page";

    private ModelAndView modelAndView = new ModelAndView(DEFAULT_PAGE_VIEW);

    private HttpServletRequest request;
    private HttpServletResponse response;

    @Getter
    private String title;

    @Getter
    private List<Component> components = Lists.newArrayList();

    @Getter
    private List<String> stylesheets = Lists.newArrayList();

    @Getter
    private List<String> javaScripts = Lists.newArrayList();

    @Getter
    private List<String> bodyJavaScripts = Lists.newArrayList();

    public AbstractPage(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public AbstractPage setTitle(String title) {
        this.title = title;
        return this;
    }

    public AbstractPage setViewName(String viewName) {
        modelAndView.setViewName(viewName);
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

    public AbstractPage addComponent(Supplier<Component> supplier) {
        Component component = checkNotNull(supplier.get(), "Component is null");
        components.add(component);
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

    public AbstractPage addStylesheet(String stylesheet) {
        stylesheets.add(stylesheet);
        return this;
    }

    public AbstractPage addJavascript(String javaScript) {
        return addJavascript(javaScript, false);
    }

    public AbstractPage addJavascript(String javaScript, boolean addToBody) {
        if (addToBody) {
            bodyJavaScripts.add(javaScript);
        } else {
            javaScripts.add(javaScript);
        }
        return this;
    }

    /**
     * 设置页面面包屑
     * <p>
     * 仅支持后端页面
     * <p>
     * 格式：["控制台","","用户管理","user","编辑用户"]
     *
     * @param breadcrumb 面包屑参数
     * @return 页面
     */
    public AbstractPage setBreadcrumb(String... breadcrumb) {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /**
     * 设置垂直菜单导航
     * <p>
     * 仅支持后端页面
     *
     * @param id       导航ID
     * @param activeId 当前激活的导航ID
     * @return 页面
     */
    public AbstractPage setVerticalNavigation(String id, String activeId) {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /**
     * @param id   子导航ID
     * @param text 徽章文本
     * @return 页面
     * @see #setVerticalNavigationBadge(String, AbstractLabel)
     */
    public AbstractPage setVerticalNavigationBadge(String id, String text) {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /**
     * @param id    子导航ID
     * @param text  徽章文本
     * @param color 徽章颜色
     * @return 页面
     * @see #setVerticalNavigationBadge(String, AbstractLabel)
     */
    public AbstractPage setVerticalNavigationBadge(String id, String text, Color color) {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /**
     * 设置垂直菜单导航徽章
     * <p>
     * 仅支持后端页面
     *
     * @param id    子导航ID
     * @param label 徽章
     * @return 页面
     */
    public AbstractPage setVerticalNavigationBadge(String id, AbstractLabel label) {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /**
     * 设置移动端顶部导航菜单标题
     * <p>
     * 仅支持移动端页面
     *
     * @param title 标题
     * @return 页面
     */
    public AbstractPage setMobileTopMenuTitle(String title) {
        return this;
    }

    /**
     * 设置移动端顶部导航菜单标题
     * <p>
     * 仅支持移动端页面
     *
     * @param title 标题
     * @param url   超链接
     * @return 页面
     */
    public AbstractPage setMobileTopMenuTitle(String title, String url) {
        return this;
    }

    /**
     * 设置移动端底部激活菜单项
     * <p>
     * 仅支持移动端页面
     *
     * @param id 导航ID
     * @return 页面
     * @see MobilePage
     */
    public AbstractPage setMobileBottomMenuActiveItem(String id) {
        return this;
    }

    /**
     * 隐藏移动端底部导航栏
     * <p>
     * 仅支持移动端页面
     *
     * @return 页面
     */
    public AbstractPage hideMobileBottomMenu() {
        return this;
    }

    /**
     * 覆盖默认的账户组件
     * <p>
     * 仅支持移动端页面
     *
     * @param profileHeader 新的账户组件
     * @return 页面
     */
    public AbstractPage setProfileHeader(ProfileHeader profileHeader) {
        return this;
    }

    public ModelAndView build() {
        modelAndView.addObject(HTML_PAGE, this);
        return modelAndView;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
