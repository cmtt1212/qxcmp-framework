package com.qxcmp.framework.message.web;

import com.qxcmp.framework.message.InnerMessage;
import com.qxcmp.framework.message.InnerMessageService;
import com.qxcmp.framework.security.Role;
import com.qxcmp.framework.security.RoleService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.web.QxcmpController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.qxcmp.framework.core.QxcmpConfiguration.QXCMP_BACKEND_URL;

/**
 * @author Tony
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/inbox")
@RequiredArgsConstructor
public class AdminStationMessageController extends QxcmpController {

    private final InnerMessageService stationMessageService;

    private final UserService userService;

    private final RoleService roleService;

    @GetMapping("")
    public ModelAndView inbox(Pageable pageable) {

        //RuntimeException::new 这是干嘛的？
        User user = currentUser().orElseThrow(RuntimeException::new);

        Page<InnerMessage> messages = stationMessageService.findByUserID(user.getId(), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "sendTime"));

        return page().addComponent(convertToTable("inbox", InnerMessage.class, messages)).build();
    }

    @GetMapping(value = "/new")
    public ModelAndView editMessage(AdminStationmessageSendForm form) {
        User user = currentUser().orElseThrow(RuntimeException::new);

        return page().addComponent(convertToForm(form))
                .addObject("selection_items_group", roleService.findAll())
                .build();
    }

    @PostMapping("/new")
    public ModelAndView sendMessage(@Valid final AdminStationmessageSendForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getModel();
            return page(viewHelper.nextWarningOverview("error", "")).build();
        }
        User user = currentUser().orElseThrow(RuntimeException::new);

        Set<User> receivers = new HashSet<User>();

        if (!form.getReceiver().isEmpty()) {
            User user1 = userService.findByUsername(form.getReceiver()).orElse(null);
            receivers.add(user1);
        }

        if (!form.getGroup().isEmpty()) {
            form.getGroup().forEach((Role group) -> {
                receivers.addAll(userService.findByRole(group));
            });
        }


        receivers.forEach(rev -> {
            stationMessageService.create(() -> {
                InnerMessage message = stationMessageService.next();
                        message.setSender(user.getUsername());
                        message.setContent(form.getContent());
                        message.setUserID(rev.getId());
                        message.setAlreadyRead(false);
                        message.setSendTime(new Date());
                        return message;
                    }
            );
        });

        return page(viewHelper.nextSuccessOverview("发送成功", "").addLink("返回", QXCMP_BACKEND_URL + "/inbox")).build();
    }
}
