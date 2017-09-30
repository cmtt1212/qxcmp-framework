package com.qxcmp.framework.spdier.web;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.spdier.SpiderContextHolder;
import com.qxcmp.framework.spdier.SpiderDefinition;
import com.qxcmp.framework.spdier.SpiderRuntime;
import com.qxcmp.framework.spdier.log.SpiderLogService;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.model.RestfulResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/spider")
@RequiredArgsConstructor
public class AdminSpiderPageController extends QXCMPBackendController {

    private final SpiderContextHolder spiderContextHolder;

    private final SpiderLogService spiderLogService;

    @GetMapping("")
    public ModelAndView spiderPage() {
        return page().addComponent(convertToTable(SpiderDefinition.class, new PageImpl<>(Lists.newArrayList(spiderContextHolder.getSpiderDefinitions().values()), new PageRequest(0, 100), spiderContextHolder.getSpiderDefinitions().values().size())))
                .setBreadcrumb("控制台", "", "系统工具", "tools", "蜘蛛管理")
                .setVerticalMenu(getVerticalMenu(""))
                .build();
    }

    @PostMapping("/remove")
    public ResponseEntity<RestfulResponse> roleBatchRemove(@RequestParam("keys[]") List<String> keys) {
        RestfulResponse restfulResponse = audit("批量禁用蜘蛛", context -> {
            try {
                for (String key : keys) {
                    spiderContextHolder.getSpiderDefinitions().values().stream().filter(definition -> definition.getName().equals(key)).findAny().ifPresent(spiderDefinition -> spiderDefinition.setDisabled(true));
                }
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        });
        return ResponseEntity.status(restfulResponse.getStatus()).body(restfulResponse);
    }

    @PostMapping("/{name}/enable")
    public ResponseEntity<RestfulResponse> spiderEnable(@PathVariable String name) {
        RestfulResponse restfulResponse = audit("启用蜘蛛", context -> {
            try {
                spiderContextHolder.getSpiderDefinitions().values().stream().filter(definition -> definition.getName().equals(name)).findAny().ifPresent(spiderDefinition -> spiderDefinition.setDisabled(false));
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        });
        return ResponseEntity.status(restfulResponse.getStatus()).body(restfulResponse);
    }

    @PostMapping("/{name}/disable")
    public ResponseEntity<RestfulResponse> spiderDisable(@PathVariable String name) {
        RestfulResponse restfulResponse = audit("禁用蜘蛛", context -> {
            try {
                spiderContextHolder.getSpiderDefinitions().values().stream().filter(definition -> definition.getName().equals(name)).findAny().ifPresent(spiderDefinition -> spiderDefinition.setDisabled(true));
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        });
        return ResponseEntity.status(restfulResponse.getStatus()).body(restfulResponse);
    }

    @GetMapping("/status")
    public ModelAndView spiderStatusPage() {
        return page().addComponent(convertToTable(SpiderRuntime.class, new PageImpl<>(Lists.newArrayList(spiderContextHolder.getSpiderRuntimeInfo()), new PageRequest(0, 100), spiderContextHolder.getSpiderRuntimeInfo().size())))
                .setBreadcrumb("控制台", "", "系统工具", "tools", "蜘蛛管理", "spider", "运行状态")
                .setVerticalMenu(getVerticalMenu("运行状态"))
                .build();
    }

    @PostMapping("/status/{name}/stop")
    public ResponseEntity<RestfulResponse> spiderStatusStop(@PathVariable String name) {
        RestfulResponse restfulResponse = audit("终止蜘蛛", context -> {

            Optional<SpiderRuntime> spiderRuntime = spiderContextHolder.getSpiderRuntimeInfo().stream().filter(runtime -> runtime.getName().equals(name)).findAny();

            if (spiderRuntime.isPresent()) {
                try {
                    spiderRuntime.get().getSpider().stop();
                } catch (Exception e) {
                    throw new ActionException("Can't stop spider " + name, e);
                }
            } else {
                throw new ActionException("No Spider Runtime Information");
            }

        });
        return ResponseEntity.status(restfulResponse.getStatus()).body(restfulResponse);
    }

    @GetMapping("/log")
    public ModelAndView spiderLogPage(Pageable pageable) {
        return page().addComponent(convertToTable(pageable, spiderLogService))
                .setBreadcrumb("控制台", "", "系统工具", "tools", "蜘蛛管理", "spider", "蜘蛛日志")
                .setVerticalMenu(getVerticalMenu("蜘蛛日志"))
                .build();
    }

    private List<String> getVerticalMenu(String activeItem) {
        return ImmutableList.of(activeItem, "运行状态", QXCMP_BACKEND_URL + "/spider/status", "蜘蛛日志", QXCMP_BACKEND_URL + "/spider/log");
    }
}
