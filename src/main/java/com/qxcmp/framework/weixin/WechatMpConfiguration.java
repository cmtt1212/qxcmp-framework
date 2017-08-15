package com.qxcmp.framework.weixin;

import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信服务配置
 * <p>
 * 配置微信服务需要用到的Spring Bean
 *
 * @author aaric
 */
@Configuration
@RequiredArgsConstructor
public class WechatMpConfiguration {

    private final WechatMpMessageHandler defaultMessageHandler;

}
