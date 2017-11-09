package com.qxcmp.framework.weixin;

import com.google.common.collect.Lists;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialCountResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNewsBatchGetResult;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 微信服务
 * <p>
 * 提供微信模块常用的服务
 *
 * @author aaric
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WeixinService {

    private static final int MAX_COUNT = 10000;

    private static final int MAX_MATERIAL_COUNT = 20;

    private final UserService userService;

    private final WxMpService wxMpService;

    private final WechatMpNewsArticleService wechatMpNewsArticleService;

    private boolean weixinUserSync;

    /**
     * 公众号图文消息缓存
     */
    private List<WxMpMaterialNews.WxMpMaterialNewsArticle> articles = Lists.newArrayList();

    /**
     * 公众号图片素材缓存
     */
    private List<WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem> images = Lists.newArrayList();

    /**
     * 公众号视频素材缓存
     */
    private List<WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem> videos = Lists.newArrayList();

    /**
     * 公众号语音素材缓存
     */
    private List<WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem> voices = Lists.newArrayList();

    /**
     * 微信网页授权登录
     * <p>
     * 根据code获取用户OpenId，如果查询到用户信息，则设置OpenId对应的用户为登录状态，并设置登录时间
     *
     * @param code Oauth2 认证码
     * @return 如果认证成功返回认证以后的用户，否则返回 empty
     */
    public Optional<User> loadOauth2User(String code) {

        if (Objects.nonNull(userService.currentUser()) || StringUtils.isBlank(code)) {
            return Optional.empty();
        }

        try {
            WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(accessToken, null);
            Optional<User> userOptional = userService.findByOpenID(wxMpUser.getOpenId());
            userOptional.ifPresent(user -> SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())));
            return userOptional;
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * 加载公众号素材
     */
    @Async
    public void loadMaterials() {
        log.info("Loading weixin materials");

        try {
            WxMpMaterialCountResult countResult = wxMpService.getMaterialService().materialCount();

            for (int i = 0; i <= countResult.getNewsCount() / MAX_MATERIAL_COUNT; i++) {
                WxMpMaterialNewsBatchGetResult newsBatchGetResult = wxMpService.getMaterialService().materialNewsBatchGet(i * MAX_MATERIAL_COUNT, i * MAX_MATERIAL_COUNT + MAX_MATERIAL_COUNT);
                newsBatchGetResult.getItems().forEach(wxMaterialNewsBatchGetNewsItem -> wxMaterialNewsBatchGetNewsItem.getContent().getArticles().forEach(wxMpMaterialNewsArticle -> {

                    WechatMpNewsArticle wechatMpNewsArticle = wechatMpNewsArticleService.next();
                    wechatMpNewsArticle.setUrl(wxMpMaterialNewsArticle.getUrl());
                    wechatMpNewsArticle.setThumbMediaId(wxMpMaterialNewsArticle.getThumbMediaId());
                    wechatMpNewsArticle.setThumbUrl(wxMpMaterialNewsArticle.getThumbUrl().replaceAll("\\?wx_fmt=jpeg", ""));
                    wechatMpNewsArticle.setAuthor(wxMpMaterialNewsArticle.getAuthor());
                    wechatMpNewsArticle.setTitle(wxMpMaterialNewsArticle.getTitle());
                    wechatMpNewsArticle.setContentSourceUrl(wxMpMaterialNewsArticle.getContentSourceUrl());
                    wechatMpNewsArticle.setContent(wxMpMaterialNewsArticle.getContent().replaceAll("data-src=\"(.*?)\"", "src=\"http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=$1\"").replaceAll("<img", "<img class=\"responsive-img\"").replaceAll("\\?wx_fmt=jpeg", ""));
                    wechatMpNewsArticle.setDigest(wxMpMaterialNewsArticle.getDigest());
                    wechatMpNewsArticle.setShowCoverPic(wxMpMaterialNewsArticle.isShowCoverPic());

                    wechatMpNewsArticleService.findByUrl(wxMpMaterialNewsArticle.getUrl()).ifPresent(wArticle -> wechatMpNewsArticle.setId(wArticle.getId()));

                    wechatMpNewsArticleService.save(wechatMpNewsArticle);
                }));
            }

            for (int i = 0; i <= countResult.getImageCount() / MAX_MATERIAL_COUNT; i++) {
                WxMpMaterialFileBatchGetResult fileBatchGetResult = wxMpService.getMaterialService().materialFileBatchGet(WxConsts.MATERIAL_IMAGE, i * MAX_MATERIAL_COUNT, i * MAX_MATERIAL_COUNT + MAX_MATERIAL_COUNT);
                images.addAll(fileBatchGetResult.getItems());
            }

            for (int i = 0; i <= countResult.getVideoCount() / MAX_MATERIAL_COUNT; i++) {
                WxMpMaterialFileBatchGetResult fileBatchGetResult = wxMpService.getMaterialService().materialFileBatchGet(WxConsts.MATERIAL_VIDEO, i * MAX_MATERIAL_COUNT, i * MAX_MATERIAL_COUNT + MAX_MATERIAL_COUNT);
                videos.addAll(fileBatchGetResult.getItems());
            }

            for (int i = 0; i <= countResult.getVoiceCount() / MAX_MATERIAL_COUNT; i++) {
                WxMpMaterialFileBatchGetResult fileBatchGetResult = wxMpService.getMaterialService().materialFileBatchGet(WxConsts.MATERIAL_VOICE, i * MAX_MATERIAL_COUNT, i * MAX_MATERIAL_COUNT + MAX_MATERIAL_COUNT);
                voices.addAll(fileBatchGetResult.getItems());
            }
        } catch (Exception e) {
            log.warn("Can't load weixin materials, cause: {}", e.getMessage());
        }
    }

    /**
     * 执行微信公众号用户同步任务
     * <p>
     * 负责查询所有微信公众号已关注用户，并与平台用户数据库同步
     */
    @Async
    public void doSync() {
        try {
            log.info("Start Wechat user sync");
            weixinUserSync = true;

            List<String> openIds = Lists.newArrayList();

            int count;

            do {
                WxMpUserList userList = wxMpService.getUserService().userList(null);
                count = userList.getCount();
                openIds.addAll(userList.getOpenids());
            } while (count == MAX_COUNT);

            openIds.forEach(s -> {
                try {
                    sync(wxMpService.getUserService().userInfo(s));
                } catch (Exception e) {
                    log.warn("Wechat user info sync failed：{}", e.getMessage());
                }
            });

            log.info("Finish Wechat user sync");
            weixinUserSync = false;
        } catch (Exception e) {
            log.error("Wechat user sync failed：{}", e.getMessage());
            weixinUserSync = false;
        }
    }

    /**
     * 同步一个微信用户
     * <p>
     * 如果OpenID对应的用户存在，则更新用户信息
     * <p>
     * 如果对应的用户不存在，则创建新的用户
     *
     * @param wxMpUser 获取到的微信用户信息
     */
    public void sync(WxMpUser wxMpUser) {

        Optional<User> userOptional = userService.findByOpenID(wxMpUser.getOpenId());

        if (userOptional.isPresent()) {
            userService.update(userOptional.get().getId(), user -> setUserWechatInfo(user, wxMpUser));
        } else {
            userService.create(() -> {
                User user = userService.next();
                user.setUsername(nextUsername());
                setUserWechatInfo(user, wxMpUser);
                return user;
            });
        }
    }

    public List<WxMpMaterialNews.WxMpMaterialNewsArticle> getArticles() {
        return articles;
    }

    public List<WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem> getImages() {
        return images;
    }

    public List<WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem> getVideos() {
        return videos;
    }

    public List<WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem> getVoices() {
        return voices;
    }

    public boolean isWeixinUserSync() {
        return weixinUserSync;
    }

    private void setUserWechatInfo(User user, WxMpUser wxMpUser) {
        user.setSubscribe(wxMpUser.getSubscribe());
        user.setOpenID(wxMpUser.getOpenId());
        user.setNickname(wxMpUser.getNickname());
        user.setSex(wxMpUser.getSex());
        user.setLanguage(wxMpUser.getLanguage());
        user.setCity(wxMpUser.getCity());
        user.setProvince(wxMpUser.getProvince());
        user.setCountry(wxMpUser.getCountry());
        user.setPortrait(wxMpUser.getHeadImgUrl());
        user.setSubscribeTime(wxMpUser.getSubscribeTime());
        user.setUnionId(wxMpUser.getUnionId());
        user.setSexId(wxMpUser.getSexId());
        user.setRemark(wxMpUser.getRemark());
        user.setGroupId(wxMpUser.getGroupId());
        user.setTagIds(wxMpUser.getTagIds());
    }

    private String nextUsername() {
        String username = RandomStringUtils.randomAlphanumeric(10);

        while (userService.findByUsername(username).isPresent()) {
            username = RandomStringUtils.randomAlphanumeric(10);
        }

        return username;
    }
}
