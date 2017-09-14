package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.domain.*;
import com.qxcmp.framework.web.QXCMPBackendController2;
import com.qxcmp.framework.web.form.AdminMallCommodityForm;
import com.qxcmp.framework.web.form.AdminMallOrderForm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 商城后台页面路由
 *
 * @author aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/mall")
@RequiredArgsConstructor
public class MallModuleController extends QXCMPBackendController2 {

    private static final int IMAGE_SIZE = 512;

    private final CommodityService commodityService;

    private final ImageService imageService;

    private final CommodityOrderService commodityOrderService;

    @GetMapping("/commodity")
    public ModelAndView commodityTable(Pageable pageable) {
        return builder().setTableView(pageable, commodityService).build();
    }

    @GetMapping("/commodity/new")
    public ModelAndView commodityNew(final AdminMallCommodityForm form) {
        return builder().setFormView(form).build();
    }

    @GetMapping("/commodity/{id}/edit")
    public ModelAndView commodityEdit(@PathVariable String id) {
        return commodityService.findOne(id).map(commodity -> {
            final AdminMallCommodityForm form = new AdminMallCommodityForm();
            form.setId(commodity.getId());
            form.setCover(commodity.getCover());
            form.setTitle(commodity.getTitle());
            form.setSubTitle(commodity.getSubTitle());
            form.setOriginPrice(commodity.getOriginPrice());
            form.setSellPrice(commodity.getSellPrice());
            form.setEnablePoints(commodity.isEnablePoints());
            form.setPoints(commodity.getPoints());
            form.setDescription(commodity.getDescription());
            form.setInventory(commodity.getInventory());
            form.setSoldOut(commodity.isSoldOut());
            form.setAlbums(commodity.getAlbums());
            form.setDetails(commodity.getDetails());
            return builder().setFormView(form).build();
        }).orElse(error(HttpStatus.NOT_FOUND, "商品不存在").build());
    }

    @PostMapping("/commodity")
    public ModelAndView commodityPost(@RequestParam(value = "upload_albums", required = false) String addAlbum,
                                      @RequestParam(value = "remove_album_albums", required = false) String removeAlbum,
                                      @RequestParam(value = "upload_details", required = false) String addDetails,
                                      @RequestParam(value = "remove_album_details", required = false) String removeDetails,
                                      @Valid @ModelAttribute(FORM_OBJECT) AdminMallCommodityForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        if (StringUtils.isNotEmpty(addAlbum)) {
            if (StringUtils.isNotEmpty(form.getAlbumsDefault().getOriginalFilename())) {
                try {
                    imageService.store(form.getAlbumsDefault().getInputStream(), FilenameUtils.getExtension(form.getAlbumsDefault().getOriginalFilename()), IMAGE_SIZE, IMAGE_SIZE).ifPresent(image -> form.getAlbums().add(String.format("/api/image/%s.%s", image.getId(), image.getType())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return builder().setFormView(form).build();
        }

        if (StringUtils.isNotEmpty(removeAlbum)) {
            try {
                Integer albumID = Integer.valueOf(removeAlbum);
                form.getAlbums().remove(albumID.intValue());
            } catch (NumberFormatException ignored) {

            }
            return builder().setFormView(form).build();
        }

        if (StringUtils.isNotEmpty(addDetails)) {
            if (StringUtils.isNotEmpty(form.getDetailsDefault().getOriginalFilename())) {
                try {
                    imageService.store(form.getDetailsDefault().getInputStream(), FilenameUtils.getExtension(form.getDetailsDefault().getOriginalFilename()), IMAGE_SIZE, IMAGE_SIZE).ifPresent(image -> form.getDetails().add(String.format("/api/image/%s.%s", image.getId(), image.getType())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return builder().setFormView(form).build();
        }

        if (StringUtils.isNotEmpty(removeDetails)) {
            try {
                Integer albumID = Integer.valueOf(removeDetails);
                form.getDetails().remove(albumID.intValue());
            } catch (NumberFormatException ignored) {

            }
            return builder().setFormView(form).build();
        }

        if (Objects.isNull(form.getId())) {
            return action("创建商品", context -> {
                try {
                    commodityService.create(() -> {
                        Commodity commodity = commodityService.next();
                        try {
                            setCommodityBaseInfo(form, commodity);
                        } catch (IOException ignored) {
                        }
                        return commodity;
                    });
                } catch (Exception e) {
                    throw new ActionException(e.getMessage());
                }
            }, (context, modelAndViewBuilder) -> modelAndViewBuilder.setResultNavigation("返回商品列表", QXCMP_BACKEND_URL + "/mall/commodity")).build();
        } else {
            return action("编辑商品", context -> {
                try {
                    commodityService.update(form.getId(), commodity -> {
                        try {
                            setCommodityBaseInfo(form, commodity);
                        } catch (IOException ignored) {
                        }
                    });
                } catch (Exception e) {
                    throw new ActionException(e.getMessage());
                }
            }, (context, modelAndViewBuilder) -> modelAndViewBuilder.setResultNavigation("返回商品列表", QXCMP_BACKEND_URL + "/mall/commodity")).build();
        }
    }

    @GetMapping("/order")
    public ModelAndView commodityOrder(Pageable pageable) {
        return builder().setTableView(new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC, "status"), commodityOrderService).build();
    }

    @GetMapping("/order/{id}")
    public ModelAndView commodityOrderDetails(@PathVariable String id) {
        return commodityOrderService.findOne(id).map(commodityOrder -> builder().addDictionaryView(dictionaryViewBuilder -> dictionaryViewBuilder.title("订单详情")
                .dictionary("订单编号", commodityOrder.getId())
                .dictionary("下单时间", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(commodityOrder.getDateCreated()))
                .dictionary("订单状态", commodityOrder.getStatus().getValue())
                .dictionary("配送地址", commodityOrder.getAddress())
                .dictionary("商品总额", String.format("￥ %s", new DecimalFormat("0.00").format((double) commodityOrder.getActualPayment() / 100)))).build()).orElse(error(HttpStatus.NOT_FOUND, "订单不存在").build());
    }

    @GetMapping("/order/{id}/edit")
    public ModelAndView commodityOrderUpdate(@PathVariable String id) {
        return commodityOrderService.findOne(id).map(commodityOrder -> {

            if (!commodityOrder.getStatus().equals(OrderStatusEnum.PAYED)) {
                return error(HttpStatus.BAD_REQUEST, "只能处理已付款的订单").build();
            }

            final AdminMallOrderForm form = new AdminMallOrderForm();
            form.setId(commodityOrder.getId());
            form.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(commodityOrder.getDateCreated()));
            form.setPayment(new DecimalFormat("0.00").format((double) commodityOrder.getActualPayment() / 100));
            form.setStatus(commodityOrder.getStatus().getValue());
            return builder().setFormView(form).build();
        }).orElse(error(HttpStatus.NOT_FOUND, "订单不存在").build());
    }

    @PostMapping("/order")
    public ModelAndView commodityOrderPost(@Valid @ModelAttribute(FORM_OBJECT) AdminMallOrderForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        if (form.isFinished()) {
            return action("处理商品订单", context -> {
                commodityOrderService.update(form.getId(), commodityOrder -> commodityOrder.setStatus(OrderStatusEnum.FINISHED));
            }).build();
        } else {
            return redirect(QXCMP_BACKEND_URL + "/mall/order");
        }
    }

    private void setCommodityBaseInfo(AdminMallCommodityForm form, Commodity commodity) throws IOException {
        commodity.setCover(form.getCover());
        commodity.setTitle(form.getTitle());
        commodity.setSubTitle(form.getSubTitle());
        commodity.setOriginPrice(form.getOriginPrice());
        commodity.setSellPrice(form.getSellPrice());
        commodity.setEnablePoints(form.isEnablePoints());
        commodity.setPoints(form.getPoints());
        commodity.setDescription(form.getDescription());
        commodity.setInventory(form.getInventory());
        commodity.setSoldOut(form.isSoldOut());
        commodity.setAlbums(form.getAlbums());
        commodity.setDetails(form.getDetails());

        if (StringUtils.isNotBlank(form.getCoverFile().getOriginalFilename())) {
            String imageType = FilenameUtils.getExtension(form.getCoverFile().getOriginalFilename());

            imageService.store(form.getCoverFile().getInputStream(), imageType, 256, 256).ifPresent(image -> commodity.setCover(String.format("/api/image/%s.%s", image.getId(), image.getType())));
        }
    }

}
