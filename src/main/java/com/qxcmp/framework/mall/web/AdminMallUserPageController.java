package com.qxcmp.framework.mall.web;

import com.qxcmp.framework.mall.Store;
import com.qxcmp.framework.mall.StoreService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.container.TextContainer;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.IconHeader;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.label.BasicLabel;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.NAVIGATION_ADMIN_MALL_USER_STORE_MANAGEMENT;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/mall/user/store")
@RequiredArgsConstructor
public class AdminMallUserPageController extends QXCMPBackendController {

    /**
     * 用户所选店铺偏好
     */
    private final String USER_CONFIG_STORE_SELECTION = "admin.mall.user.store.selection";

    private final StoreService storeService;

    /**
     * 用户先进行店铺选择，然后进行相关店铺的处理
     *
     * @return 店铺选择页面
     */
    @GetMapping("")
    public ModelAndView userStorePage() {

        User user = currentUser().orElseThrow(RuntimeException::new);

        List<Store> stores = storeService.findByUser(user);

        if (stores.isEmpty()) {
            return overviewPage(new Overview(new IconHeader("你还没有管理任何店铺", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/mall")).build();
        }

        Store selectedStore = getUserSelectedStore(user);

        if (Objects.isNull(selectedStore) || !stores.contains(selectedStore)) {
            return redirect(QXCMP_BACKEND_URL + "/mall/user/store/select");
        } else {
            return page().addComponent(new Segment()
                    .addComponent(getUserStorePageHeader(selectedStore)))
                    .setBreadcrumb("控制台", "", "商城管理", "mall", "我的店铺")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MALL_USER_STORE_MANAGEMENT, "")
                    .build();
        }
    }

    @GetMapping("/select")
    public ModelAndView userStoreSelectPage(final AdminMallUserStoreSelectForm form) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        List<Store> stores = storeService.findByUser(user);

        if (stores.isEmpty()) {
            return overviewPage(new Overview(new IconHeader("你还没有管理任何店铺", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/mall")).build();
        }

        form.setStore(stores.get(0));

        return page().addComponent(new TextContainer().addComponent(new Segment()
                .addComponent(new PageHeader(HeaderType.H4, "请选择店铺"))
                .addComponent(convertToForm(form))))
                .setBreadcrumb("控制台", "", "商城管理", "mall", "我的店铺", "mall/user/store", "选择店铺")
                .addObject("selection_items_store", stores)
                .addObject(form)
                .build();
    }

    @PostMapping("/select")
    public ModelAndView userStoreSelectPage(@Valid final AdminMallUserStoreSelectForm form, BindingResult bindingResult) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        List<Store> stores = storeService.findByUser(user);

        if (bindingResult.hasErrors() || !stores.contains(form.getStore())) {
            return overviewPage(new Overview(new IconHeader("店铺不存在", new Icon("warning circle"))).addLink("返回", QXCMP_BACKEND_URL + "/mall")).build();
        }

        userConfigService.save(user.getId(), USER_CONFIG_STORE_SELECTION, form.getStore().getId());

        return redirect(QXCMP_BACKEND_URL + "/mall/user/store");
    }

    private Store getUserSelectedStore(User user) {
        return storeService.findOne(userConfigService.getString(user.getId(), USER_CONFIG_STORE_SELECTION).orElse("")).orElse(null);
    }

    private Component getUserStorePageHeader(Store selectedStore) {
        return new BasicLabel(selectedStore.getName()).setIcon(new Icon("marker")).setUrl(QXCMP_BACKEND_URL + "/mall/user/store/select");
    }
}
