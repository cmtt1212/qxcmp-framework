package com.qxcmp.framework.weixin;

import com.qxcmp.framework.view.ModelAndViewBuilder;
import org.springframework.stereotype.Component;

/**
 * 微信公众号页面帮助类
 * <p>
 * 提供以下支持 <ol> <li>添加JS-SDK配置</li> </ol>
 *
 * @author aaric
 */
@Component
public class WechatPageHelper {

    /**
     * 向一个模型视图注入JS-SDK配置，模板使用该配置进行JS-SDK接入
     *
     * @param builder 模型视图构建器
     * @param apiList JS-SDK 要用到的API列表
     *
     * @return 加入API列表后的模型视图
     */
    public ModelAndViewBuilder addJsApiConfig(ModelAndViewBuilder builder, String... apiList) {
        return builder.addObject("apiList", apiList).addObject("jsApiDebug", false);
    }

    /**
     * 向一个模型视图注入JS-SDK配置，模板使用该配置进行JS-SDK接入，并开启调试模式
     *
     * @param builder 模型视图构建器
     * @param apiList JS-SDK 要用到的API列表
     *
     * @return 加入API列表后的模型视图
     */
    public ModelAndViewBuilder addJsApiConfigDebug(ModelAndViewBuilder builder, String... apiList) {
        return builder.addObject("apiList", apiList).addObject("jsApiDebug", true);
    }
}
