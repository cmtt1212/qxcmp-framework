package com.qxcmp.framework.message;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkState;

/**
 * 消息服务总接口
 * <p>
 * 该服务用户向用户发送消息
 *
 * @author Aaric
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    private final FeedService feedService;
    private final EmailService emailService;
    private final SmsService smsService;
    private final UserService userService;

    /**
     * 为用户生成一个Feed
     * <p>
     * 必须指定Feed Owner
     * 不用指定Feed 时间
     *
     * @param feed 要生成的Feed
     */
    @Async
    public void feed(Feed feed) {
        checkState(StringUtils.isNotBlank(feed.getOwner()), "Empty feed owner");
        feed.setId(null);
        feed.setDateCreated(new Date());
        feedService.save(feed);
    }

    /**
     * 为用户生成一个Feed
     * <p>
     * 必须指定Feed Owner
     * 不用指定Feed 时间
     *
     * @param feedConsumer Feed消费者
     */
    @Async
    public void feed(Consumer<Feed> feedConsumer) {
        Feed feed = feedService.next();
        feedConsumer.accept(feed);
        feed(feed);
    }

    /**
     * 为用户生成一个Feed ，并发送邮件到该用户
     *
     * @param feedConsumer Feed消费者
     * @param subject      邮件主题
     * @param content      邮件内容
     */
    @Async
    public void email(Consumer<Feed> feedConsumer, String subject, String content) {
        Feed feed = feedService.next();
        feedConsumer.accept(feed);

        String ownerId = feed.getOwner();
        feed(feed);

        sendEmail(subject, content, ownerId);
    }

    /**
     * 为用户生成一个Feed，并发送邮件到该用户，再发送短信到该用户
     *
     * @param feedConsumer Feed消费者
     * @param subject      邮件主题
     * @param content      邮件内容
     * @param templateCode 短信模板Code
     * @param parameters   短信参数
     */
    public void sms(Consumer<Feed> feedConsumer, String subject, String content, String templateCode, Consumer<Map<String, String>> parameters) {
        Feed feed = feedService.next();
        feedConsumer.accept(feed);

        String ownerId = feed.getOwner();
        feed(feed);

        sendEmail(subject, content, ownerId);
        sendSms(templateCode, parameters, ownerId);
    }

    private void sendSms(String templateCode, Consumer<Map<String, String>> parameters, String ownerId) {
        try {
            userService.findOne(ownerId).filter(user -> StringUtils.isNotBlank(user.getPhone())).ifPresent(user -> {
                smsService.send(ImmutableList.of(user.getPhone()), templateCode, parameters);
            });
        } catch (Exception ignored) {

        }
    }

    private void sendEmail(String subject, String content, String ownerId) {
        try {
            userService.findOne(ownerId).filter(user -> StringUtils.isNotBlank(user.getEmail())).ifPresent(user -> {
                emailService.send(mimeMessageHelper -> {
                    mimeMessageHelper.setTo(user.getEmail());
                    mimeMessageHelper.setSubject(subject);
                    mimeMessageHelper.setText(content);
                });
            });
        } catch (Exception ignored) {

        }
    }
}
