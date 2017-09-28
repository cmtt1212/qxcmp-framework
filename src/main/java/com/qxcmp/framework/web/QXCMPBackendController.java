package com.qxcmp.framework.web;

import com.google.gson.Gson;
import com.qxcmp.framework.audit.Action;
import com.qxcmp.framework.audit.ActionExecutor;
import com.qxcmp.framework.core.QXCMPNavigationConfiguration;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.model.RestfulResponse;
import com.qxcmp.framework.web.model.navigation.NavigationService;
import com.qxcmp.framework.web.view.BackendPage;
import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.elements.header.IconHeader;
import com.qxcmp.framework.web.view.elements.html.P;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.menu.Menu;
import com.qxcmp.framework.web.view.elements.menu.RightMenu;
import com.qxcmp.framework.web.view.elements.menu.VerticalSubMenu;
import com.qxcmp.framework.web.view.elements.menu.item.*;
import com.qxcmp.framework.web.view.modules.accordion.AccordionItem;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Fixed;
import com.qxcmp.framework.web.view.views.Overview;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.NAVIGATION_QXCMP_ADMIN_SIDEBAR;

public abstract class QXCMPBackendController extends AbstractQXCMPController {

    private NavigationService navigationService;

    private ActionExecutor actionExecutor;

    @Override
    protected BackendPage page() {
        BackendPage page = applicationContext.getBean(BackendPage.class, request, response);
        page.setTopMenu(new Menu().setInverted().setFixed(Fixed.TOP).addItem(new LogoImageItem(siteService.getLogo(), siteService.getTitle())).setRightMenu((RightMenu) new RightMenu().addItem(new BackendAccountMenuItem(currentUser().orElseThrow(() -> new RuntimeException("No user authenticated for backend")), navigationService.get(QXCMPNavigationConfiguration.NAVIGATION_QXCMP_ADMIN_ACCOUNT).getItems()))));
        page.setBottomMenu(new Menu().setInverted().setFixed(Fixed.BOTTOM).addItem(new SidebarIconItem()).setRightMenu((RightMenu) new RightMenu().addItem(new TextItem("关于", QXCMP_BACKEND_URL + "/about"))));
        addPageSidebarContent(page, currentUser().orElse(null));
        return page;
    }

    protected ModelAndView submitForm(Object form, Action action) {
        return submitForm(form, action, (stringObjectMap, overview) -> {
        });
    }

    /**
     * 提交一个表单并执行相应操作
     * <p>
     * 该操作会被记录到审计日志中
     *
     * @param form       要提交的表单
     * @param action     要执行的操作
     * @param biConsumer 返回的结果页面
     * @return 提交后的页面
     */
    protected ModelAndView submitForm(Object form, Action action, BiConsumer<Map<String, Object>, Overview> biConsumer) {
        String title = null;
        Form annotation = form.getClass().getAnnotation(Form.class);

        if (Objects.nonNull(annotation)) {
            title = annotation.value();
        }

        if (StringUtils.isBlank(title)) {
            title = request.getRequestURL().toString();
        }

        return actionExecutor.execute(title, request.getRequestURL().toString(), getRequestContent(request), currentUser().orElse(null), action)
                .map(auditLog -> {
                    Overview overview = null;
                    switch (auditLog.getStatus()) {
                        case SUCCESS:
                            overview = new Overview(new IconHeader(auditLog.getTitle(), new Icon("info circle")).setSubTitle("操作成功"));
                            break;
                        case FAILURE:
                            overview = new Overview(new IconHeader(auditLog.getTitle(), new Icon("warning circle").setColor(Color.RED)).setSubTitle("操作失败")).addComponent(new P(auditLog.getComments()));
                            break;
                    }


                    biConsumer.accept(auditLog.getActionContext(), overview);

                    if (overview.getLinks().isEmpty()) {
                        overview.addLink("返回", request.getRequestURL().toString());
                    }

                    return overviewPage(overview).build();
                }).orElse(overviewPage(new Overview(new IconHeader("保存操作结果失败", new Icon("warning circle"))).addLink("返回", request.getRequestURL().toString())).build());
    }

    protected RestfulResponse audit(String title, Action action) {
        return actionExecutor.execute(title, request.getRequestURL().toString(), getRequestContent(request), currentUser().orElse(null), action).map(auditLog -> {

            switch (auditLog.getStatus()) {
                case SUCCESS:
                    return new RestfulResponse(HttpStatus.OK.value(), "", auditLog.getTitle(), auditLog.getComments());
                case FAILURE:
                    return new RestfulResponse(HttpStatus.BAD_GATEWAY.value(), "", auditLog.getTitle(), auditLog.getComments());
            }

            return new RestfulResponse(HttpStatus.NOT_ACCEPTABLE.value(), "", auditLog.getTitle(), auditLog.getComments());

        }).orElse(new RestfulResponse(HttpStatus.BAD_GATEWAY.value(), "", "Can't save audit log"));
    }

    private void addPageSidebarContent(BackendPage page, User user) {
        navigationService.get(NAVIGATION_QXCMP_ADMIN_SIDEBAR).getItems().forEach(navigation -> {
            if (navigation.isVisible(user)) {
                if (navigation.getItems().isEmpty()) {
                    page.addSideContent(new TextItem(navigation.getTitle(), navigation.getAnchor().getHref()).setLink());
                } else {
                    if (navigation.getItems().stream().anyMatch(n -> n.isVisible(user))) {

                        VerticalSubMenu verticalMenu = new VerticalSubMenu();

                        navigation.getItems().forEach(item -> {
                            if (item.isVisible(user)) {
                                verticalMenu.addItem(new TextItem(item.getTitle(), item.getAnchor().getHref()));
                            }
                        });

                        AccordionItem accordionItem = new AccordionItem();
                        accordionItem.setTitle(navigation.getTitle());
                        accordionItem.setContent(verticalMenu);

                        page.addSideContent(new AccordionMenuItem(accordionItem).setLink());
                    }
                }
            }
        });
    }

    private String getRequestContent(HttpServletRequest request) {
        if (request.getMethod().equalsIgnoreCase("get")) {
            return request.getQueryString();
        } else if (request.getMethod().equalsIgnoreCase("post")) {
            return new Gson().toJson(request.getParameterMap());
        } else {
            return "Unknown request method: " + request.toString();
        }
    }

    @Autowired
    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    @Autowired
    public void setActionExecutor(ActionExecutor actionExecutor) {
        this.actionExecutor = actionExecutor;
    }
}
