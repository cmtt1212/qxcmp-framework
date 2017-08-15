package com.qxcmp.framework.core;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.qxcmp.framework.config.SystemConfigAutowired;
import com.qxcmp.framework.config.SystemConfigService;
import com.qxcmp.framework.security.PrivilegeAutowired;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * 清醒内容管理平台总配置
 * <p>
 * 包含了平台通用常量
 * <p>
 * 负责创建Spring IoC初始Bean
 *
 * @author aaric
 */
@Configuration
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
@PrivilegeAutowired
@SystemConfigAutowired
public class QXCMPConfiguration {

    /**
     * 平台后端根Url
     */
    public static final String QXCMP_BACKEND_URL = "/admin";

    /**
     * 是否开放用户名注册
     */
    public static String SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME = "";

    /**
     * 是否开放邮箱注册
     */
    public static String SYSTEM_CONFIG_ACCOUNT_ENABLE_EMAIL = "";

    /**
     * 是否开放手机号注册
     */
    public static String SYSTEM_CONFIG_ACCOUNT_ENABLE_PHONE = "";

    /**
     * 是否开放激活码注册
     */
    public static String SYSTEM_CONFIG_ACCOUNT_ENABLE_INVITE = "";

    /**
     * 任务调度线程池初始大小
     */
    public static String SYSTEM_CONFIG_TASK_EXECUTOR_CORE_POOL_SIZE = "task.executor.core.pool.size";
    public static Integer SYSTEM_CONFIG_TASK_EXECUTOR_CORE_POOL_SIZE_DEFAULT_VALUE = 10;

    /**
     * 任务调度线程池最大线程数量
     */
    public static String SYSTEM_CONFIG_TASK_EXECUTOR_MAX_POOL_SIZE = "task.executor.max.pool.size";
    public static Integer SYSTEM_CONFIG_TASK_EXECUTOR_MAX_POOL_SIZE_DEFAULT_VALUE = 50;

    /**
     * 任务调度队列最大数量
     */
    public static String SYSTEM_CONFIG_TASK_EXECUTOR_QUEUE_CAPACITY = "task.executor.queue.capacity";
    public static Integer SYSTEM_CONFIG_TASK_EXECUTOR_QUEUE_CAPACITY_DEFAULT_VALUE = 100;

    /**
     * 会话超时时间
     */
    public static String SYSTEM_CONFIG_SESSION_TIMEOUT = "session.timeout";
    public static Integer SYSTEM_CONFIG_SESSION_TIMEOUT_DEFAULT_VALUE = 3600;

    /**
     * 每个用户最大会话数量
     */
    public static String SYSTEM_CONFIG_SESSION_MAX_ACTIVE_COUNT = "session.max.active.count";
    public static Integer SYSTEM_CONFIG_SESSION_MAX_ACTIVE_COUNT_DEFAULT_VALUE = 1;

    /**
     * 用户达到最大会话数量以后是否阻止继续登录，如果不阻止继续登录则之前的会话过期
     */
    public static String SYSTEM_CONFIG_SESSION_MAX_PREVENT_LOGIN = "session.max.prevent.login";
    public static Boolean SYSTEM_CONFIG_SESSION_MAX_PREVENT_LOGIN_DEFAULT_VALUE = false;

    /**
     * 网站域名
     */
    public static String SYSTEM_CONFIG_SITE_DOMAIN = "";

    /**
     * 网站页面默认标题
     */
    public static String SYSTEM_CONFIG_SITE_TITLE = "";

    /**
     * 网站页面关键字
     */
    public static String SYSTEM_CONFIG_SITE_KEYWORDS = "";

    /**
     * 网站页面描述
     */
    public static String SYSTEM_CONFIG_SITE_DESCRIPTION = "";

    /**
     * 网站LOGO
     */
    public static String SYSTEM_CONFIG_SITE_LOGO = "";
    public static String SYSTEM_CONFIG_SITE_LOGO_DEFAULT_VALUE = "/assets/images/logo.png";

    /**
     * 网站页面图标
     */
    public static String SYSTEM_CONFIG_SITE_FAVICON = "";
    public static String SYSTEM_CONFIG_SITE_FAVICON_DEFAULT_VALUE = "/assets/icons/favicon.png";


    /**
     * 邮件服务 - 主机名称
     */
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME = "message.email.hostname";
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME_DEFAULT_VALUE = "smtp.qq.com";

    /**
     * 邮件服务 - 端口号
     */
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_PORT = "message.email.port";
    public static int SYSTEM_CONFIG_MESSAGE_EMAIL_PORT_DEFAULT_VALUE = 465;

    /**
     * 邮件服务 - 用户名
     */
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME = "message.email.username";
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME_DEFAULT_VALUE = "qxcmp@foxmail.com";

    /**
     * 邮件服务 - 密码
     */
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD = "message.email.password";
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD_DEFAULT_VALUE = "krdhkmdilrhwceed";

    /**
     * 邮件服务 - 密码重置邮件主题
     * <p>
     * 该配置可以设置发送的密码重置邮件主题
     */
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_SUBJECT = "message.email.account.reset.subject";
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_SUBJECT_DEFAULT_VALUE = "请重置您的密码";

    /**
     * 邮件服务 - 密码重置邮件内容
     */
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_CONTENT = "message.email.account.reset.content";
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_CONTENT_DEFAULT_VALUE = "点击该链接来重置您的密码: ${link}";

    /**
     * 邮件服务 - 账户激活邮件主题
     */
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_SUBJECT = "message.email.account.activate.subject";
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_SUBJECT_DEFAULT_VALUE = "请激活您的账户";

    /**
     * 邮件服务 - 账户激活邮件内容
     */
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_CONTENT = "message.email.account.activate.content";
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_CONTENT_DEFAULT_VALUE = "点击该链接来激活您的账户： ${link}";

    /**
     * 邮件服务 - 账户绑定邮件主题
     */
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_SUBJECT = "message.email.account.binding.subject";
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_SUBJECT_DEFAULT_VALUE = "账户绑定验证码";

    /**
     * 邮件服务 - 账户绑定邮件内容
     */
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_CONTENT = "message.email.account.binding.content";
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_CONTENT_DEFAULT_VALUE = "您的账户绑定验证码为： ${captcha}";

    /**
     * 短信服务 - 阿里云AccessKey
     */
    public static String SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY = "message.sms.accessKey";
    public static String SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY_DEFAULT_VALUE = "";

    /**
     * 短信服务 - 阿里云AccessSecret
     */
    public static String SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET = "message.sms.accessSecret";
    public static String SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET_DEFAULT_VALUE = "";

    /**
     * 短信服务 - 阿里云EndPoint
     */
    public static String SYSTEM_CONFIG_MESSAGE_SMS_END_POINT = "message.sms.endPoint";
    public static String SYSTEM_CONFIG_MESSAGE_SMS_END_POINT_DEFAULT_VALUE = "";

    /**
     * 短信服务 - 阿里云TopicRef
     */
    public static String SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF = "message.sms.topicRef";
    public static String SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF_DEFAULT_VALUE = "";

    /**
     * 短信服务 - 阿里云短信模板签名
     */
    public static String SYSTEM_CONFIG_MESSAGE_SMS_SIGN = "message.sms.sign";
    public static String SYSTEM_CONFIG_MESSAGE_SMS_SIGN_DEFAULT_VALUE = "";

    /**
     * 短信服务 - 验证码短信阿里云模板代码
     */
    public static String SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE = "message.sms.captcha.template.code";
    public static String SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE_DEFAULT_VALUE = "";

    public static String SYSTEM_CONFIG_AUTHENTICATION_CAPTCHA_THRESHOLD = "";
    public static Integer SYSTEM_CONFIG_AUTHENTICATION_CAPTCHA_THRESHOLD_DEFAULT_VALUE = 3;

    public static String SYSTEM_CONFIG_AUTHENTICATION_CAPTCHA_LENGTH = "";
    public static Integer SYSTEM_CONFIG_AUTHENTICATION_CAPTCHA_LENGTH_DEFAULT_VALUE = 4;

    public static String SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK = "";
    public static Boolean SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_DEFAULT_VALUE = false;

    public static String SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_THRESHOLD = "";
    public static Integer SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_THRESHOLD_DEFAULT_VALUE = 5;

    public static String SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_DURATION = "";
    public static Integer SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_DURATION_DEFAULT_VALUE = 15;

    public static String SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_EXPIRE = "";
    public static Boolean SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_EXPIRE_DEFAULT_VALUE = false;

    public static String SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_EXPIRE_DURATION = "";
    public static Integer SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_EXPIRE_DURATION_DEFAULT_VALUE = 180;

    public static String SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_EXPIRE = "";
    public static Boolean SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_EXPIRE_DEFAULT_VALUE = false;

    public static String SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_EXPIRE_DURATION = "";
    public static Integer SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_EXPIRE_DURATION_DEFAULT_VALUE = 90;

    public static String SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_UNIQUE = "";
    public static Boolean SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_UNIQUE_DEFAULT_VALUE = false;

    /**
     * 字典初始化标志，平台在第一次启动的时候会初始化默认字典
     */
    public static String SYSTEM_CONFIG_DICTIONARY_INITIAL_FLAG = "";

    /**
     * 是否开启水平功能
     */
    public static String SYSTEM_CONFIG_IMAGE_WATERMARK_ENABLE = "";
    public static boolean SYSTEM_CONFIG_IMAGE_WATERMARK_ENABLE_DEFAULT_VALUE = true;

    /**
     * 水印名称
     */
    public static String SYSTEM_CONFIG_IMAGE_WATERMARK_NAME = "";

    /**
     * 水印位置
     */
    public static String SYSTEM_CONFIG_IMAGE_WATERMARK_POSITION = "";
    public static int SYSTEM_CONFIG_IMAGE_WATERMARK_POSITION_DEFAULT_VALUE = Positions.BOTTOM_RIGHT.ordinal();

    /**
     * 水印字体大小
     */
    public static String SYSTEM_CONFIG_IMAGE_WATERMARK_FONT_SIZE = "";
    public static int SYSTEM_CONFIG_IMAGE_WATERMARK_FONT_SIZE_DEFAULT_VALUE = 14;


    /**
     * 是否开启微信支付功能
     */
    public static String SYSTEM_CONFIG_WECHAT_PAYMENT_ENABLE;

    /**
     * 默认为关闭微信支付功能
     */
    public static boolean SYSTEM_CONFIG_WECHAT_PAYMENT_ENABLE_DEFAULT_VALUE = false;

    /**
     * 微信公众号 App Id
     */
    public static String SYSTEM_CONFIG_WECHAT_APP_ID = "wechat.app.id";

    /**
     * 微信支付 - 商户号
     */
    public static String SYSTEM_CONFIG_WECHAT_MCH_ID = "wechat.mch.id";

    /**
     * 微信支付 - 商户密钥
     */
    public static String SYSTEM_CONFIG_WECHAT_MCH_KEY = "wechat.mch.key";

    /**
     * 服务商模式下的子商户公众账号ID
     */
    public static String SYSTEM_CONFIG_WECHAT_SUB_APP_ID = "wechat.sub.app.id";

    /**
     * 服务商模式下的子商户号
     */
    public static String SYSTEM_CONFIG_WECHAT_SUB_MCH_ID = "wechat.sub.mch.id";

    /**
     * apiclient_cert.p12的文件的绝对路径
     */
    public static String SYSTEM_CONFIG_WECHAT_KEY_PATH = "wechat.key.path";

    /**
     * 微信支付结果通知Url,Url以【域名】/api/pay/result/mp 的形式组成
     */
    public static String SYSTEM_CONFIG_WECHAT_NOTIFY_URL = "wechat.notify.url";

    /**
     * 平台是否开启微信支付
     */
    public static String SYSTEM_CONFIG_FINANCE_PAYMENT_SUPPORT_WEIXIN;

    /**
     * 默认为否，引入微信支付依赖以后自动设为开启状态
     */
    public static boolean SYSTEM_CONFIG_FINANCE_PAYMENT_SUPPORT_WEIXIN_DEFAULT_VALUE = false;

    /**
     * 平台微信支付默认使用的支付方式
     */
    public static String SYSTEM_CONFIG_WECHAT_PAYMENT_DEFAULT_TRADE_TYPE;
    /**
     * 默认为扫描支付，如果为公众号开发需要修改为公众号支付
     */
    public static String SYSTEM_CONFIG_WECHAT_PAYMENT_DEFAULT_TRADE_TYPE_DEFAULT_VALUE = "NATIVE";

    /**
     * 平台是否开始支付宝支付
     */
    public static String SYSTEM_CONFIG_FINANCE_PAYMENT_SUPPORT_ALIPAY;

    /**
     * 默认为否，引入支付宝依赖以后自动设为开启状态
     */
    public static boolean SYSTEM_CONFIG_FINANCE_PAYMENT_SUPPORT_ALIPAY_DEFAULT_VALUE = false;
    /**
     * 平台默认名称
     * <p>
     * 配置在 Spring Boot application.yml 文件里面
     */
    @Value("${application.name}")
    private String applicationName;

    private final SystemConfigService systemConfigService;

    public String getDomain() {
        return systemConfigService.getString(SYSTEM_CONFIG_SITE_DOMAIN).orElse("");
    }

    public String getTitle() {
        return systemConfigService.getString(SYSTEM_CONFIG_SITE_TITLE).orElse(applicationName);
    }

    public String getKeywords() {
        return systemConfigService.getString(SYSTEM_CONFIG_SITE_KEYWORDS).orElse("");
    }

    public String getDescription() {
        return systemConfigService.getString(SYSTEM_CONFIG_SITE_DESCRIPTION).orElse("");
    }

    public String getLogo() {
        return systemConfigService.getString(SYSTEM_CONFIG_SITE_LOGO).orElse(SYSTEM_CONFIG_SITE_LOGO_DEFAULT_VALUE);
    }

    public String getFavicon() {
        return systemConfigService.getString(SYSTEM_CONFIG_SITE_FAVICON).orElse(SYSTEM_CONFIG_SITE_FAVICON_DEFAULT_VALUE);
    }

    /**
     * 平台任务调度配置
     *
     * @return 任务调度通用对象
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(systemConfigService.getInteger(SYSTEM_CONFIG_TASK_EXECUTOR_CORE_POOL_SIZE).orElse(SYSTEM_CONFIG_TASK_EXECUTOR_CORE_POOL_SIZE_DEFAULT_VALUE));
        threadPoolTaskExecutor.setMaxPoolSize(systemConfigService.getInteger(SYSTEM_CONFIG_TASK_EXECUTOR_MAX_POOL_SIZE).orElse(SYSTEM_CONFIG_TASK_EXECUTOR_MAX_POOL_SIZE_DEFAULT_VALUE));
        threadPoolTaskExecutor.setQueueCapacity(systemConfigService.getInteger(SYSTEM_CONFIG_TASK_EXECUTOR_QUEUE_CAPACITY).orElse(SYSTEM_CONFIG_TASK_EXECUTOR_QUEUE_CAPACITY_DEFAULT_VALUE));
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    /**
     * 邮件发送服务配置
     *
     * @return 邮件发送服务对象
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setProtocol("smtp");
        javaMailSender.setHost(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME_DEFAULT_VALUE));
        javaMailSender.setPort(systemConfigService.getInteger(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT_DEFAULT_VALUE));
        javaMailSender.setUsername(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME_DEFAULT_VALUE));
        javaMailSender.setPassword(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD_DEFAULT_VALUE));

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME_DEFAULT_VALUE));
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.port", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT).orElse(String.valueOf(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT_DEFAULT_VALUE)));
        properties.put("mail.smtp.socketFactory.port", javaMailSender.getPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.timeout", "5000");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

    /**
     * 微信支付配置
     *
     * @return 微信支付配置实例
     */
    @Bean
    public WxPayConfig wxPayConfig() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_APP_ID).orElse(""));
        wxPayConfig.setMchId(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_MCH_ID).orElse(""));
        wxPayConfig.setMchKey(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_MCH_KEY).orElse(""));
        wxPayConfig.setSubAppId(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_SUB_APP_ID).orElse(""));
        wxPayConfig.setSubMchId(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_SUB_MCH_ID).orElse(""));
        wxPayConfig.setNotifyUrl(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_NOTIFY_URL).orElse(""));
        wxPayConfig.setKeyPath(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_KEY_PATH).orElse(""));
        wxPayConfig.setTradeType("JSAPI");
        return wxPayConfig;
    }

    /**
     * 微信支付服务实例配置，全局唯一
     *
     * @return 微信支付实例
     */
    @Bean
    public WxPayService wxPayService() {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig());
        return wxPayService;
    }
}
