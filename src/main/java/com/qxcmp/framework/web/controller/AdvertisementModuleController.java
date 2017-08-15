package com.qxcmp.framework.web.controller;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.domain.Advertisement;
import com.qxcmp.framework.domain.AdvertisementService;
import com.qxcmp.framework.domain.Image;
import com.qxcmp.framework.domain.ImageService;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.form.AdminAdForm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 广告模块后端页面路由
 *
 * @author aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/ad")
@RequiredArgsConstructor
public class AdvertisementModuleController extends QXCMPBackendController {

    public static final List<String> SUPPORT_TYPES = ImmutableList.of("横幅", "弹框", "摩天楼");

    private final AdvertisementService advertisementService;

    private final ImageService imageService;

    @GetMapping
    public ModelAndView adTable(Pageable pageable) {
        return builder().setTitle("广告列表")
                .setTableView(pageable, advertisementService)
                .addNavigation("广告列表", Navigation.Type.NORMAL, "系统工具")
                .build();
    }

    @GetMapping("/new")
    public ModelAndView adNew(final AdminAdForm form) {
        return builder().setTitle("创建广告")
                .setFormView(form, SUPPORT_TYPES)
                .addNavigation("广告列表", Navigation.Type.NORMAL, "系统工具")
                .build();
    }

    @GetMapping("/{id}/edit")
    public ModelAndView adEdit(@PathVariable String id) {
        return advertisementService.findOne(id).map(advertisement -> {
            final AdminAdForm form = new AdminAdForm();
            form.setId(advertisement.getId());
            form.setType(advertisement.getType());
            form.setTitle(advertisement.getTitle());
            form.setLink(advertisement.getLink());
            form.setImage(advertisement.getImage());
            form.setAdOrder(advertisement.getAdOrder());
            return builder().setTitle("编辑广告")
                    .setFormView(form, SUPPORT_TYPES)
                    .addNavigation("广告列表", Navigation.Type.NORMAL, "系统工具")
                    .build();
        }).orElse(error(HttpStatus.NOT_FOUND, "广告不存在").build());
    }

    @PostMapping
    public ModelAndView adPost(@Valid @ModelAttribute(FORM_OBJECT) AdminAdForm form, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form, SUPPORT_TYPES).build();
        }

        Image image = null;

        if (StringUtils.isNotBlank(form.getPortrait().getOriginalFilename())) {
            String fileType = FilenameUtils.getExtension(form.getPortrait().getOriginalFilename());
            image = imageService.store(form.getPortrait().getInputStream(), fileType, 1280, 1280).orElseThrow(IOException::new);
        }

        final Image finalImage = image;

        if (Objects.isNull(form.getId())) {
            return action("创建广告", context -> {
                advertisementService.create(() -> {
                    Advertisement advertisement = advertisementService.next();

                    if (Objects.nonNull(finalImage)) {
                        form.setImage(String.format("/api/image/%s.%s", finalImage.getId(), finalImage.getType()));
                    }

                    setAdvertisementBaseInfo(form, advertisement);
                    return advertisement;
                });
            }).build();
        } else {
            return action("编辑广告", context -> {
                advertisementService.update(form.getId(), advertisement -> {
                    if (Objects.nonNull(finalImage)) {
                        form.setImage("/api/image/" + finalImage.getId());
                    }
                    setAdvertisementBaseInfo(form, advertisement);
                });
            }).build();
        }
    }

    @PostMapping("/{id}/delete")
    public ModelAndView adRemove(@PathVariable String id) {
        return action("删除广告", context -> advertisementService.remove(Long.parseLong(id)), (context, modelAndViewBuilder) -> modelAndViewBuilder.setResultNavigation("返回广告列表", QXCMP_BACKEND_URL + "/ad")).build();
    }

    private void setAdvertisementBaseInfo(AdminAdForm form, Advertisement advertisement) {
        advertisement.setTitle(form.getTitle());
        advertisement.setType(form.getType());
        advertisement.setLink(form.getLink());
        advertisement.setImage(form.getImage());
        advertisement.setAdOrder(form.getAdOrder());
    }
}
