package com.qxcmp.framework.user.web;

import com.qxcmp.framework.security.RoleService;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.elements.button.Buttons;
import com.qxcmp.framework.web.view.elements.container.TextContainer;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.header.IconHeader;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.table.dictionary.CollectionValueCell;
import com.qxcmp.framework.web.view.support.AnchorTarget;
import com.qxcmp.framework.web.view.support.Size;
import com.qxcmp.framework.web.view.support.Wide;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/user")
@RequiredArgsConstructor
public class AdminUserPageController extends QXCMPBackendController {

    private final RoleService roleService;

    @GetMapping("")
    public ModelAndView userPage(Pageable pageable) {
        return page().addComponent(convertToTable(pageable, userService))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "用户管理")
                .build();
    }

    @GetMapping("/{id}/details")
    public ModelAndView userDetailsPage(@PathVariable String id) {
        return userService.findOne(id).map(user -> page()
                .addComponent(new VerticallyDividedGrid().setVerticallyPadded()
                        .addItem(new Row().addCol(new Col().setGeneralWide(Wide.SIXTEEN)
                                .addComponent(new Buttons()
                                        .addButton(new Button("编辑用户角色", QXCMP_BACKEND_URL + "/user/" + id + "/role", AnchorTarget.BLANK).setBasic().setSecondary()))
                        ))
                        .addItem(new Row()
                                .addCol(new Col().setComputerWide(Wide.TEN).setMobileWide(Wide.SIXTEEN)
                                        .addComponent(new ContentHeader("基本资料", Size.NONE).setDividing())
                                        .addComponent(convertToTable(stringObjectMap -> {
                                            stringObjectMap.put("用户名", user.getUsername());
                                            stringObjectMap.put("邮箱", user.getEmail());
                                            stringObjectMap.put("手机", user.getPhone());
                                            stringObjectMap.put("真实姓名", user.getName());
                                            stringObjectMap.put("昵称", user.getNickname());
                                            stringObjectMap.put("性别", user.getSex());
                                            stringObjectMap.put("生日", user.getBirthday());
                                            stringObjectMap.put("语言", user.getLanguage());
                                            stringObjectMap.put("城市", user.getCity());
                                            stringObjectMap.put("省份", user.getProvince());
                                            stringObjectMap.put("国家", user.getCountry());
                                            stringObjectMap.put("个性签名", user.getPersonalizedSignature());
                                            stringObjectMap.put("备注", user.getRemark());
                                        })))
                                .addCol(new Col().setComputerWide(Wide.SIX).setMobileWide(Wide.SIXTEEN)
                                        .addComponent(new Image(user.getPortrait()).setCentered().setCircular().setSize(Size.SMALL))
                                        .addComponent(new ContentHeader("账户资料", Size.NONE).setDividing())
                                        .addComponent(convertToTable(stringObjectMap -> {
                                            stringObjectMap.put("UUID", user.getId());
                                            stringObjectMap.put("OpenID", user.getOpenID());
                                            stringObjectMap.put("上次登录时间", user.getDateLogin());
                                            stringObjectMap.put("拥有角色", new CollectionValueCell(user.getRoles(), "name"));
                                            stringObjectMap.put("账户是否过期", !user.isAccountNonExpired());
                                            stringObjectMap.put("账户是否锁定", !user.isAccountNonLocked());
                                            stringObjectMap.put("账户密码是否过期", !user.isCredentialsNonExpired());
                                            stringObjectMap.put("账户是否可用", !user.isEnabled());
                                        })))
                        )
                )
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "用户管理", QXCMP_BACKEND_URL + "/user", "用户详情")
                .build()
        ).orElse(overviewPage(new Overview(new IconHeader("用户不存在", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/user")).build());
    }

    @GetMapping("/{id}/role")
    public ModelAndView userRolePage(@PathVariable String id, final AdminUserRoleForm form) {
        return userService.findOne(id).map(user -> {
            form.setUsername(user.getUsername());
            form.setRoles(user.getRoles());
            return page()
                    .addComponent(new TextContainer().addComponent(new Segment().addComponent(convertToForm(form))))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "用户管理", QXCMP_BACKEND_URL + "/user", "用户详情", QXCMP_BACKEND_URL + "/user/" + id + "/details", "编辑用户角色")
                    .addObject("selection_items_roles", roleService.findAll())
                    .build();
        }).orElse(overviewPage(new Overview(new IconHeader("用户不存在", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/user")).build());
    }

    @PostMapping("/{id}/role")
    public ModelAndView userRolePage(@PathVariable String id, @Valid final AdminUserRoleForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page()
                    .addComponent(new TextContainer().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form)))))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "用户管理", QXCMP_BACKEND_URL + "/user", "用户详情", QXCMP_BACKEND_URL + "/user/" + id + "/details", "编辑用户角色")
                    .addObject("selection_items_roles", roleService.findAll())
                    .build();
        }

        return submitForm(form, context -> userService.update(id, user -> user.setRoles(form.getRoles())), (stringObjectMap, overview) -> overview.addLink("返回", QXCMP_BACKEND_URL + "/user/" + id + "/details"));
    }

}
