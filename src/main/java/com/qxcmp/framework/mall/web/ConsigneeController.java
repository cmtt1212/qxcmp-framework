package com.qxcmp.framework.mall.web;

import com.google.common.collect.Sets;
import com.qxcmp.framework.exception.ShoppingCartServiceException;
import com.qxcmp.framework.mall.Consignee;
import com.qxcmp.framework.mall.ConsigneeService;
import com.qxcmp.framework.mall.ShoppingCartService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.QxcmpController;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.grid.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/mall/consignee")
@RequiredArgsConstructor
public class ConsigneeController extends QxcmpController {

    private final ConsigneeService consigneeService;

    private final ShoppingCartService shoppingCartService;

    private final MallPageHelper mallPageHelper;

    @GetMapping("")
    public ModelAndView consigneePage() {

        User user = currentUser().orElseThrow(RuntimeException::new);

        List<Consignee> consignees = consigneeService.findByUser(user.getId());

        if (consignees.isEmpty()) {
            return redirect("/mall/consignee/new");
        }

        return page().addComponent(mallPageHelper.nextMobileConsignee(consignees))
                .build();
    }

    @GetMapping("/select")
    public ModelAndView consigneeSelectPage(@RequestParam String id) throws ShoppingCartServiceException {

        User user = currentUser().orElseThrow(RuntimeException::new);

        shoppingCartService.update(shoppingCartService.findByUserId(user.getId()).getId(), shoppingCart -> shoppingCart.setConsigneeId(id));

        return redirect("/mall/cart/order");

    }

    @GetMapping("/new")
    public ModelAndView consigneeNewPage(final MallConsigneeNewForm form) {
        return page().addComponent(new Grid().setContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(convertToForm(form)))))
                .build();
    }

    @PostMapping("/new")
    public ModelAndView consigneeNewPage(@Valid final MallConsigneeNewForm form, BindingResult bindingResult,
                                         @RequestParam(value = "add_labels", required = false) boolean addLabels,
                                         @RequestParam(value = "remove_labels", required = false) Integer removeLabels) {

        if (addLabels) {
            form.getLabels().add("");
            return page().addComponent(new Grid().setContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(convertToForm(form)))))
                    .build();
        }

        if (Objects.nonNull(removeLabels)) {
            form.getLabels().remove(removeLabels.intValue());
            return page().addComponent(new Grid().setContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(convertToForm(form)))))
                    .build();
        }

        User user = currentUser().orElseThrow(RuntimeException::new);

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Grid().setContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))))
                    .build();
        }

        consigneeService.create(() -> {
            final Consignee consignee = consigneeService.next();
            consignee.setUserId(user.getId());
            consignee.setName(form.getLabels().isEmpty() ? form.getConsigneeName() : form.getLabels().stream().findFirst().orElse(""));
            consignee.setConsigneeName(form.getConsigneeName());
            consignee.setAddress(form.getAddress());
            consignee.setTelephone(form.getTelephone());
            consignee.setEmail(form.getEmail());
            consignee.setDateCreated(new Date());
            consignee.setDateModified(new Date());
            consignee.setLabels(Sets.newLinkedHashSet(form.getLabels()));
            return consignee;
        }).ifPresent(consignee -> {
            try {
                shoppingCartService.update(shoppingCartService.findByUserId(user.getId()).getId(), shoppingCart -> shoppingCart.setConsigneeId(consignee.getId()));
            } catch (ShoppingCartServiceException e) {
                e.printStackTrace();
            }
        });

        return redirect("/mall/cart/order");
    }
}
