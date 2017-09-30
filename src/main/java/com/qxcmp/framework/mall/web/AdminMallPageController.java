package com.qxcmp.framework.mall.web;

import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.mall.Store;
import com.qxcmp.framework.mall.StoreService;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.html.P;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.NAVIGATION_ADMIN_MALL;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.NAVIGATION_ADMIN_MALL_STORE;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/mall")
@RequiredArgsConstructor
public class AdminMallPageController extends QXCMPBackendController {

    private final StoreService storeService;

    @GetMapping("")
    public ModelAndView mallPage() {
        return page().addComponent(new Overview("商城管理"))
                .setBreadcrumb("控制台", "", "商城管理")
                .setVerticalNavigation(NAVIGATION_ADMIN_MALL, "")
                .build();
    }

    @GetMapping("/store")
    public ModelAndView mallStorePage(Pageable pageable) {
        return page().addComponent(convertToTable("store", pageable, storeService))
                .setBreadcrumb("控制台", "", "商城管理", "mall", "店铺管理")
                .setVerticalNavigation(NAVIGATION_ADMIN_MALL, NAVIGATION_ADMIN_MALL_STORE)
                .build();
    }

    @GetMapping("/store/new")
    public ModelAndView mallStoreNewPage(final AdminMallStoreNewForm form) {
        return page().addComponent(new Segment()
                .addComponent(new P("注：创建好的店铺将无法删除，需要在创建的时候指定店铺所有者，所有者可指定店铺管理员"))
                .addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", "", "商城管理", "mall", "店铺管理", "mall/store", "创建店铺")
                .setVerticalNavigation(NAVIGATION_ADMIN_MALL, NAVIGATION_ADMIN_MALL_STORE)
                .addObject("selection_items_owner", userService.findAll())
                .addObject("selection_items_admins", userService.findAll())
                .build();
    }

    @PostMapping("/store/new")
    public ModelAndView mallStoreNewPage(@Valid final AdminMallStoreNewForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", "", "商城管理", "mall", "店铺管理", "mall/store", "创建店铺")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MALL, NAVIGATION_ADMIN_MALL_STORE)
                    .addObject("selection_items_owner", userService.findAll())
                    .addObject("selection_items_admins", userService.findAll())
                    .build();
        }

        return submitForm(form, context -> {
            try {
                storeService.create(() -> {
                    Store store = storeService.next();
                    store.setCover(form.getCover());
                    store.setName(form.getName());
                    store.setOwner(form.getOwner());
                    store.setDateCreated(new Date());
                    return store;
                });
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        }, (stringObjectMap, overview) -> overview.addLink("返回店铺管理", QXCMP_BACKEND_URL + "/mall/store").addLink("继续新建店铺", QXCMP_BACKEND_URL + "/mall/store/new"));
    }
}
