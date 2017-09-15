package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.config.SystemConfigService;
import com.qxcmp.framework.core.QXCMPSystemConfigConfiguration;
import com.qxcmp.framework.domain.DepositOrder;
import com.qxcmp.framework.domain.DepositOrderService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.view.dictionary.DictionaryView;
import com.qxcmp.framework.view.list.ListView;
import com.qxcmp.framework.view.list.ListViewItem;
import com.qxcmp.framework.view.pagination.Pagination;
import com.qxcmp.framework.view.support.PaginationHelper;
import com.qxcmp.framework.web.QXCMPFrontendController2;
import com.qxcmp.framework.web.form.FinancePayPasswordChangeForm;
import com.qxcmp.framework.web.form.FinancePayPasswordCreateForm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 财务页面路由
 *
 * @author aaric
 */
@Controller
@RequiredArgsConstructor
public class FinanceModuleController extends QXCMPFrontendController2 {

    private final DepositOrderService depositOrderService;

    private final SystemConfigService systemConfigService;

    private final PaginationHelper paginationHelper;

    /**
     * 交易密码设置页面
     *
     * @return 交易密码设置页面
     */
    @GetMapping("/finance/password")
    public ModelAndView passwordGet() {

        User user = currentUser();

        if (StringUtils.isNotBlank(user.getPayPassword())) {
            return builder().setFormView(new FinancePayPasswordChangeForm()).build();
        } else {
            return builder().setFormView(new FinancePayPasswordCreateForm()).build();
        }
    }

    @PostMapping("/finance/password/new")
    public ModelAndView payPasswordCreate(@Valid @ModelAttribute("object") FinancePayPasswordCreateForm form, BindingResult bindingResult) {

        if (!StringUtils.equals(form.getPassword(), form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "PasswordConfirm");
        }

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        try {
            userService.update(currentUser().getId(), user -> {
                user.setPayPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
                user.getPasswordHistory().add(user.getPayPassword());
            }).ifPresent(user -> SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())));
        } catch (Exception ignored) {

        }

        return builder().setResult("设置交易密码成功", "").build();
    }

    @PostMapping("/finance/password/change")
    public ModelAndView payPasswordChange(@Valid @ModelAttribute("object") FinancePayPasswordChangeForm form, BindingResult bindingResult) {

        if (!new BCryptPasswordEncoder().matches(form.getOriginPassword(), currentUser().getPayPassword())) {
            bindingResult.rejectValue("originPassword", "BadCredential");
        }

        if (!StringUtils.equals(form.getNewPassword(), form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "PasswordConfirm");
        }

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        try {
            userService.update(currentUser().getId(), user -> {
                user.setPayPassword(new BCryptPasswordEncoder().encode(form.getNewPassword()));
                user.getPasswordHistory().add(user.getPayPassword());
            }).ifPresent(user -> SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())));
        } catch (Exception ignored) {

        }

        return builder().setResult("修改交易密码成功", "").build();
    }

    /**
     * 获取钱包充值页面
     * <p>
     * 该页面作为整个平台资金来源的唯一入口，即平台的所有消费必须先充值到钱包以后才能进行消费
     *
     * @return 钱包充值页面
     */
    @GetMapping("/finance/deposit")
    public ModelAndView depositGet() {

        boolean supportWeixin = systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_FINANCE_PAYMENT_SUPPORT_WEIXIN).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_FINANCE_PAYMENT_SUPPORT_WEIXIN_DEFAULT_VALUE);
        boolean supportAlipay = systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_FINANCE_PAYMENT_SUPPORT_ALIPAY).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_FINANCE_PAYMENT_SUPPORT_ALIPAY_DEFAULT_VALUE);

        String weixinTradeType = systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_PAYMENT_DEFAULT_TRADE_TYPE).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_PAYMENT_DEFAULT_TRADE_TYPE_DEFAULT_VALUE);
        String weixinActionUrl = "";
        if (StringUtils.equals(weixinTradeType, "NATIVE")) {
            weixinActionUrl = "/api/wxmp-cgi/pay/native";
        } else if (StringUtils.equals(weixinTradeType, "JSAPI")) {
            weixinActionUrl = "/api/wxmp-cgi/pay/mp";
        }

        return builder().setTitle("充值中心")
                .setResult("充值中心", "")
                .addFragment("qxcmp/weixin-mp", "deposit-form")
                .addObject("supportWeixin", supportWeixin)
                .addObject("weixinAction", weixinActionUrl)
                .addObject("supportAlipay", supportAlipay)
                .build();
    }

    /**
     * 获取充值记录页面
     *
     * @param pageable 分页信息
     *
     * @return 充值记录分页查询页面
     */
    @GetMapping("/finance/deposit/order")
    public ModelAndView depositList(Pageable pageable) {
        Page<DepositOrder> depositOrders = depositOrderService.findAll(new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "timeStart"));
        Pagination pagination = paginationHelper.next(depositOrders, "/finance/deposit/order");
        List<ListViewItem> items = depositOrders.getContent().stream().map(depositOrder -> ListViewItem.builder()
                .title(String.format("【%s】交易金额：%s", depositOrder.getStatus().getValue(), new DecimalFormat("￥0.00").format((double) depositOrder.getFee() / 100)))
                .description(String.format("交易时间：%s", new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(depositOrder.getTimeStart())))
                .link("/finance/deposit/order/" + depositOrder.getId())
                .build()).collect(Collectors.toList());

        if (items.isEmpty()) {
            return builder().setTitle("充值记录")
                    .setResult("充值记录", "您还没有任何充值记录")
                    .setResultNavigation("前往充值", "/finance/deposit").build();
        }

        return builder().setTitle("充值记录")
                .setResult("充值记录", "")
                .addListView(ListView.builder().items(items).pagination(pagination).build())
                .setResultNavigation("继续充值", "/finance/deposit")
                .build();
    }

    /**
     * 充值订单详情页面
     *
     * @param id 订单号
     *
     * @return 充值订单详情页面
     */
    @GetMapping("/finance/deposit/order/{id}")
    public ModelAndView depositDetails(@PathVariable String id) {
        Optional<DepositOrder> depositOrderOptional = depositOrderService.findOne(id);

        if (!depositOrderOptional.isPresent() || !StringUtils.equals(depositOrderOptional.get().getUserId(), currentUser().getId())) {
            return error(HttpStatus.NOT_FOUND, "充值记录不存在").build();
        }

        DepositOrder depositOrder = depositOrderOptional.get();

        return builder().setTitle("充值记录详情")
                .setResult("充值记录", depositOrder.getStatus().getValue())
                .addDictionaryView(extractDepositOrderToDictionaryView(depositOrder))
                .setResultNavigation("返回充值记录", "/finance/deposit/order")
                .build();
    }

    private DictionaryView extractDepositOrderToDictionaryView(DepositOrder depositOrder) {
        return DictionaryView.builder()
                .dictionary("订单号", depositOrder.getId())
                .dictionary("充值金额", new DecimalFormat("￥0.00").format((double) depositOrder.getFee() / 100))
                .dictionary("充值时间", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(depositOrder.getTimeStart()))
                .dictionary("备注", depositOrder.getDescription())
                .build();
    }
}
