package com.qxcmp.framework.weixin;

import lombok.Builder;
import lombok.Data;

/**
 * 微信公众号分享功能信息存放Bean
 *
 * @author aaric
 */
@Data
@Builder
public class WxMpShareBean {

    /**
     * 分享链接的标题
     */
    private String title;

    /**
     * 分享链接
     */
    private String link;

    /**
     * 分享图片
     */
    private String imgUrl;

    /**
     * 分享描述信息
     */
    private String desc;

}
