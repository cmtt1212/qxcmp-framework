package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.audit.AuditLogService;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.web.QXCMPBackendController;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 日志管理模块页面路由
 *
 * @author aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/log")
@AllArgsConstructor
public class LogModuleController extends QXCMPBackendController {

    private AuditLogService auditLogService;

    @GetMapping("/audit")
    public ModelAndView actionTable(Pageable pageable) {
        return builder().setTitle("审计日志")
                .setTableView(pageable, auditLogService)
                .addNavigation("审计日志", Navigation.Type.NORMAL, "系统日志")
                .build();
    }

    @GetMapping("/audit/{id}")
    public ModelAndView actionGet(@PathVariable String id) {
        try {
            return auditLogService.findOne(Long.valueOf(id)).map(auditLog -> builder().addDictionaryView(dictionaryViewBuilder -> {
                dictionaryViewBuilder.title("审计日志详情");
                dictionaryViewBuilder.dictionary("操作标识", auditLog.getId().toString());
                dictionaryViewBuilder.dictionary("操作名称", auditLog.getTitle());
                dictionaryViewBuilder.dictionary("操作人", auditLog.getOwner() == null ? "无" : auditLog.getOwner().getUsername());
                dictionaryViewBuilder.dictionary("操作链接", auditLog.getUrl());
                dictionaryViewBuilder.dictionary("操作时间", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(auditLog.getDateCreated()));
                dictionaryViewBuilder.dictionary("操作状态", auditLog.getStatus().getValue());
                dictionaryViewBuilder.dictionary("操作内容", auditLog.getContent());
                dictionaryViewBuilder.dictionary("备注信息", auditLog.getComments());
            }).build())
                    .orElse(error(HttpStatus.NOT_FOUND, "审计日志不存在").build());
        } catch (NumberFormatException exception) {
            return error(HttpStatus.NOT_FOUND, "审计日志不存在").build();
        }

    }
}
