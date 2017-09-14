package com.qxcmp.framework.view.component;

import lombok.Builder;
import lombok.Data;

/**
 * 超链接视图组件
 *
 * @author aaric
 */
@Data
@Builder
public class Link {

    /**
     * 超链接标题
     */
    private String title;

    /**
     * 超链接Url
     */
    private String href;

    /**
     * 超链接打开方式，默认为当前窗口打开
     */
    @Builder.Default
    private String target = AnchorTarget.SELF.toString();

    /**
     * 如果超链接表示下载文件，此选项表示下载文件名
     */
    private String download;

}
