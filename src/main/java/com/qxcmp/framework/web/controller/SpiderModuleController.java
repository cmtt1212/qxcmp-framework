package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.spdier.SpiderContextHolder;
import com.qxcmp.framework.spdier.SpiderDefinition;
import com.qxcmp.framework.spdier.SpiderRuntime;
import com.qxcmp.framework.spdier.log.SpiderLogService;
import com.qxcmp.framework.view.component.ElementType;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.view.support.TableViewHelper;
import com.qxcmp.framework.view.table.TableViewEntity;
import com.qxcmp.framework.web.QXCMPBackendController2;
import com.qxcmp.framework.web.form.SpiderDefinitionForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 蜘蛛管理页面路由
 *
 * @author aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/spider")
@RequiredArgsConstructor
public class SpiderModuleController extends QXCMPBackendController2 {

    private final SpiderLogService spiderLogService;

    private final SpiderContextHolder spiderContextHolder;

    private final TableViewHelper tableViewHelper;

    @GetMapping
    public ModelAndView spider() {
        return builder().setTitle("蜘蛛管理")
                .addElement(ElementType.H4, "蜘蛛管理")
                .addNavigation(Navigation.Type.NORMAL, "蜘蛛管理")
                .build();
    }


    @GetMapping("/log")
    public ModelAndView logTable(Pageable pageable) {
        return builder().setTitle("蜘蛛日志")
                .setTableView(pageable, spiderLogService)
                .addNavigation("蜘蛛日志", Navigation.Type.NORMAL, "系统日志")
                .build();
    }

    @GetMapping("/config")
    public ModelAndView configTable() {
        Page<SpiderDefinition> page = new PageImpl<>(new ArrayList<>(spiderContextHolder.getSpiderDefinitions().values()));
        TableViewEntity<SpiderDefinition> spiderDefinitionTableViewEntity = tableViewHelper.next(SpiderDefinition.class, page);
        return builder().setTitle("蜘蛛配置")
                .setTableView(spiderDefinitionTableViewEntity)
                .addNavigation("蜘蛛配置", Navigation.Type.NORMAL, "蜘蛛管理")
                .build();
    }

    @GetMapping("/config/{name}/edit")
    public ModelAndView configGet(@PathVariable String name) {

        return spiderContextHolder.getSpiderDefinitions().values().stream().filter(definition -> definition.getName().equals(name)).findAny()
                .map(spiderDefinition -> {
                    SpiderDefinitionForm form = new SpiderDefinitionForm();
                    form.setName(spiderDefinition.getName());
                    form.setGroup(spiderDefinition.getGroup());
                    form.setThread(spiderDefinition.getThread());
                    form.setOrder(spiderDefinition.getOrder());
                    form.setDisabled(spiderDefinition.isDisabled());
                    return builder().setFormView(form).build();
                }).orElse(error(HttpStatus.NOT_FOUND, "Spider Definition not exist").build());
    }

    @PostMapping("/config")
    public ModelAndView configPost(@Valid @ModelAttribute("object") SpiderDefinitionForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        return action("修改蜘蛛配置", context -> {
            spiderContextHolder.getSpiderDefinitions().values().stream().filter(spiderDefinition -> spiderDefinition.getGroup().equals(form.getGroup()) && spiderDefinition.getName().equals(form.getName()))
                    .findAny().ifPresent(spiderDefinition -> {
                spiderDefinition.setThread(form.getThread());
                spiderDefinition.setOrder(form.getOrder());
                spiderDefinition.setDisabled(form.getDisabled());
            });
        }).build();
    }

    @GetMapping("/status")
    public ModelAndView statusTable() {
        Page<SpiderRuntime> page = new PageImpl<>(new ArrayList<>(spiderContextHolder.getSpiderRuntimeInfo()));
        TableViewEntity<SpiderRuntime> spiderRuntimeTableViewEntity = tableViewHelper.next(SpiderRuntime.class, page);
        return builder().setTitle("蜘蛛状态")
                .setTableView(spiderRuntimeTableViewEntity)
                .addNavigation("蜘蛛状态", Navigation.Type.NORMAL, "蜘蛛管理")
                .build();
    }

    @PostMapping("/status/{name}")
    public ModelAndView statusStop(@PathVariable String name) {
        return action("停止蜘蛛", context -> {
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
        }, (context, modelAndViewBuilder) -> modelAndViewBuilder.setResultNavigation("返回", QXCMP_BACKEND_URL + "/spider/status")).build();
    }
}
