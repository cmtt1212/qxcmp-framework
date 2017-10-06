package com.qxcmp.framework.web.page;

import com.qxcmp.framework.web.view.Component;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * 页面对象
 * <p>
 * 包含了渲染页面所有的信息
 *
 * @author aaric
 */
@Getter
@Setter
public abstract class AbstractFrontendPage extends AbstractPage {

    /**
     * 移动端底部菜单激活导航栏项目ID
     */
    private String mobileActiveNavigation;

    public AbstractFrontendPage(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public AbstractFrontendPage addComponent(Component component) {
        super.addComponent(component);
        return this;
    }

    @Override
    public AbstractFrontendPage addComponents(Collection<? extends Component> components) {
        super.addComponents(components);
        return this;
    }
}
