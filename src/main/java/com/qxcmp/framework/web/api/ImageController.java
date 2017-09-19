package com.qxcmp.framework.web.api;

import com.qxcmp.framework.core.QXCMPSystemConfigConfiguration;
import com.qxcmp.framework.core.validation.ImageValidator;
import com.qxcmp.framework.domain.ImageService;
import com.qxcmp.framework.web.QXCMPFrontendController2;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 图片Web API
 * <p>
 * 负责访问并返回图片内容，没有权限控制
 * <p>
 * 需要激活 Spring Profile {@code api}
 *
 * @author aaric
 */
@Controller
@RequestMapping("/api/image/")
@RequiredArgsConstructor
public class ImageController extends QXCMPFrontendController2 {

    private final ImageService imageService;

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        try {
            return imageService.findOne(id).map(image ->
                    ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.parseMediaType(String.format("image/%s", image.getType())))
                            .body(image.getContent()))
                    .orElse(ResponseEntity.notFound().build());
        } catch (NumberFormatException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}.{type}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id, @PathVariable String type) {
        try {
            return imageService.findOne(id).filter(image -> image.getType().equalsIgnoreCase(type)).map(image ->
                    ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.parseMediaType(String.format("image/%s", image.getType())))
                            .body(image.getContent()))
                    .orElse(ResponseEntity.notFound().build());
        } catch (NumberFormatException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile file) {
        try {
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

            if (new ImageValidator().isValid(file, null)) {
                return imageService.store(file.getInputStream(), fileExtension).map(image -> {

                    if (systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_ENABLE).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_ENABLE_DEFAULT_VALUE)) {
                        imageService.addWatermark(image, siteService.getTitle(), Positions.values()[systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_POSITION).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_POSITION_DEFAULT_VALUE)], systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_FONT_SIZE).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_FONT_SIZE_DEFAULT_VALUE));
                    }

                    return ResponseEntity.ok(String.format("/api/image/%s.%s", image.getId(), image.getType()));
                })
                        .orElse(ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("upload failed"));
            } else {
                return ResponseEntity.badRequest().body("仅支持以下图片格式:jpg,gif,png");
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }
}
