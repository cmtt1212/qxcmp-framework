package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.mall.Consignee;
import com.qxcmp.framework.mall.ConsigneeService;
import com.qxcmp.framework.mall.ShoppingCartService;
import com.qxcmp.framework.view.list.ListView;
import com.qxcmp.framework.view.list.ListViewItem;
import com.qxcmp.framework.web.QXCMPFrontendController2;
import com.qxcmp.framework.web.form.MallConsigneeForm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 收货人页面相关路由
 *
 * @author aaric
 */
@RequiredArgsConstructor
public class ConsigneeController extends QXCMPFrontendController2 {

    private final ConsigneeService consigneeService;

    @GetMapping
    public ModelAndView consignee() {

        List<ListViewItem> items = consigneeService.findByUser(currentUser().getId()).stream().map(consignee -> ListViewItem.builder()
                .title(String.format("%s %s", consignee.getName(), consignee.getTelephone())).description(consignee.getAddress())
                .link(String.format("/mall/consignee/%s/edit", consignee.getId()))
                .build()).collect(Collectors.toList());

        if (items.isEmpty()) {
            return builder().setResult("我的收货地址", "您还没有收获地址").setResultNavigation("马上添加", "/mall/consignee/new").build();
        } else {
            return builder().setResult("我的收获地址", "").addListView(ListView.builder().items(items).build()).setResultNavigation("新建地址", "/mall/consignee/new").build();
        }
    }

    @GetMapping("/new")
    public ModelAndView newConsignee(final MallConsigneeForm form) {
        return builder().setFormView(form).build();
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editConsignee(@PathVariable String id) {
        return consigneeService.findOne(id).map(consignee -> {
            final MallConsigneeForm form = new MallConsigneeForm();
            form.setId(consignee.getId());
            form.setName(consignee.getName());
            form.setConsigneeName(consignee.getConsigneeName());
            form.setAddress(consignee.getAddress());
            form.setTelephone(consignee.getTelephone());
            form.setEmail(consignee.getEmail());
            return builder().setFormView(form).build();
        }).orElse(error(HttpStatus.NOT_FOUND, "收货人不存在").build());
    }

    @PostMapping
    public ModelAndView consignee(@Valid @ModelAttribute(FORM_OBJECT) MallConsigneeForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        try {
            if (StringUtils.isEmpty(form.getId())) {
                consigneeService.create(() -> {
                    final Consignee consignee = consigneeService.next();
                    consignee.setUserId(currentUser().getId());
                    consignee.setName(form.getName());
                    consignee.setConsigneeName(form.getConsigneeName());
                    consignee.setAddress(form.getAddress());
                    consignee.setTelephone(form.getTelephone());
                    consignee.setEmail(form.getEmail());
                    consignee.setDateCreated(new Date());
                    consignee.setDateModified(new Date());
                    return consignee;
                });
            } else {
                consigneeService.update(form.getId(), consignee -> {
                    consignee.setUserId(currentUser().getId());
                    consignee.setName(form.getName());
                    consignee.setConsigneeName(form.getConsigneeName());
                    consignee.setAddress(form.getAddress());
                    consignee.setTelephone(form.getTelephone());
                    consignee.setEmail(form.getEmail());
                    consignee.setDateModified(new Date());
                });
            }
            return redirect("/mall/consignee");
        } catch (Exception e) {
            return error(HttpStatus.BAD_GATEWAY, e.getMessage()).build();
        }
    }
}
