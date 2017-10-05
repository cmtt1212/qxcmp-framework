package com.qxcmp.framework.weixin;

import lombok.Data;

import javax.persistence.*;

/**
 * 公众号图文素材实体
 * <p>
 * 用于持久化保存公众号图文素材
 *
 * @author aaric
 */
@Entity
@Table
@Data
public class WechatMpNewsArticle {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String url;

    private String thumbMediaId;

    private String thumbUrl;

    private String author;

    private String title;

    private String contentSourceUrl;

    @Lob
    private String content;

    private String digest;

    private boolean showCoverPic;

}
