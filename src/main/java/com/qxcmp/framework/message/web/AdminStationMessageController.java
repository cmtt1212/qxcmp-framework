package com.qxcmp.framework.message.web;

import com.qxcmp.framework.web.QxcmpController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * @author Tony
 */
@Controller
//@RequestMapping(QXCMP_BACKEND_URL + "/inner/message")
@RequiredArgsConstructor
public class AdminStationMessageController extends QxcmpController {
//
//    private final InnerMessageService stationMessageService;
//
//    private final UserService userService;
//
//    private final RoleService roleService;
//
//    @GetMapping("")
//    public ModelAndView inbox(Pageable pageable) {
//
//        //RuntimeException::new 这是干嘛的？
//        User user = currentUser().orElseThrow(RuntimeException::new);
//
//        Page<InnerMessage> messages = stationMessageService.findByUserIdOrderBySendTimeDesc(user.getId(), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "sendTime"));
//
//        return page().addComponent(convertToTable("inbox", InnerMessage.class, messages)).build();
//    }
//
//    @GetMapping(value = "/new")
//    public ModelAndView editMessage(AdminMessageInnerMessageNewForm form) {
//        User user = currentUser().orElseThrow(RuntimeException::new);
//
//        return page().addComponent(convertToForm(form))
//                .addObject("selection_items_group", roleService.findAll())
//                .build();
//    }
//
//    @PostMapping("/new")
//    public ModelAndView sendMessage(@Valid final AdminMessageInnerMessageNewForm form, BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            bindingResult.getModel();
//            return page(viewHelper.nextWarningOverview("error", "")).build();
//        }
//        User user = currentUser().orElseThrow(RuntimeException::new);
//
//        Set<User> receivers = new HashSet<User>();
//
//        if (!form.getReceiver().isEmpty()) {
//            User user1 = userService.findByUsername(form.getReceiver()).orElse(null);
//            receivers.add(user1);
//        }
//
//        if (!form.getGroup().isEmpty()) {
//            form.getGroup().forEach((Role group) -> {
//                receivers.addAll(userService.findByRole(group));
//            });
//        }
//
//
//        receivers.forEach(rev -> {
//            stationMessageService.create(() -> {
//                        InnerMessage message = stationMessageService.next();
//                        message.setSender(user.getUsername());
//                        message.setContent(form.getContent());
//                        message.setUserId(rev.getId());
//                        message.setRead(false);
//                        message.setSendTime(new Date());
//                        return message;
//                    }
//            );
//        });
//
//        return page(viewHelper.nextSuccessOverview("发送成功", "").addLink("返回", QXCMP_BACKEND_URL + "/inbox")).build();
//    }
}
