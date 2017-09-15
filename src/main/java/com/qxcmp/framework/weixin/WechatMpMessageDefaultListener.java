package com.qxcmp.framework.weixin;

import com.qxcmp.framework.config.SystemConfigService;
import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.weixin.event.WechatMpSubscribeEvent;
import com.qxcmp.framework.weixin.event.WechatMpUnsubscribeEvent;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.qxcmp.framework.core.QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SUBSCRIBE_WELCOME_MESSAGE;

/**
 * 平台默认微信消息事件监听器
 * <p>
 * 负责处理关注和取关事件
 *
 * @author aaric
 */
@Component
@RequiredArgsConstructor
public class WechatMpMessageDefaultListener {

    private final WechatService wechatService;

    private final UserService userService;

    private final SystemConfigService systemConfigService;

    @EventListener
    public void onSubscribe(WechatMpSubscribeEvent event) {
        try {
            WxMpUser wxMpUser = event.getWxMpService().getUserService().userInfo(event.getWxMpXmlMessage().getFromUser());

            wechatService.sync(wxMpUser);

            event.setWxMpXmlOutMessage(WxMpXmlOutMessage.TEXT().content(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_SUBSCRIBE_WELCOME_MESSAGE).orElse("")).build());
        } catch (WxErrorException e) {
            event.setWxMpXmlOutMessage(WxMpXmlOutMessage.TEXT().content("关注失败，平台出现错误: " + e.getMessage()).build());
        }
    }

    @EventListener
    public void onUnsubscribe(WechatMpUnsubscribeEvent event) {
        userService.update(event.getWxMpXmlMessage().getFromUser(), user -> user.setSubscribe(false));
    }
}
