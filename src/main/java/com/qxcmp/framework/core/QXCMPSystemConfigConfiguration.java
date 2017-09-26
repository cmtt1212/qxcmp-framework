package com.qxcmp.framework.core;

import com.qxcmp.framework.config.SystemConfigAutowired;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.context.annotation.Configuration;

/**
 * 平台系统配置
 *
 * @author Aaric
 */
@Configuration
@SystemConfigAutowired
public class QXCMPSystemConfigConfiguration {
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
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_SUBJECT_DEFAULT_VALUE = "请重置你的密码";
    /**
     * 邮件服务 - 密码重置邮件内容
     */
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_CONTENT = "message.email.account.reset.content";
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_CONTENT_DEFAULT_VALUE = "点击该链接来重置你的密码: ${link}";
    /**
     * 邮件服务 - 账户激活邮件主题
     */
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_SUBJECT = "message.email.account.activate.subject";
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_SUBJECT_DEFAULT_VALUE = "请激活你的账户";
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
    public static String SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_CONTENT_DEFAULT_VALUE = "你的账户绑定验证码为： ${captcha}";
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
     * 微信公众号 App Secret
     */
    public static String SYSTEM_CONFIG_WECHAT_SECRET = "wechat.secret";
    /**
     * 微信公众号 Token
     */
    public static String SYSTEM_CONFIG_WECHAT_TOKEN = "wechat.token";
    /**
     * 微信公众号 AES Key
     */
    public static String SYSTEM_CONFIG_WECHAT_AES_KEY = "wechat.aes.key";
    /**
     * 微信公众号网页授权回调 URL
     */
    public static String SYSTEM_CONFIG_WECHAT_OAUTH2_CALLBACK_URL = "";
    /**
     * 微信公众号网页授权URL，用户在微信客户端点击该链接后将出现网页授权指示
     */
    public static String SYSTEM_CONFIG_WECHAT_OAUTH2_AUTHORIZATION_URL;
    /**
     * 微信公众号关注欢迎语
     */
    public static String SYSTEM_CONFIG_WECHAT_SUBSCRIBE_WELCOME_MESSAGE = "";
    /**
     * 是否开启微信调试模式
     */
    public static String SYSTEM_CONFIG_WECHAT_DEBUG = "";
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
     * 是否启用兑换码功能
     */
    public static String SYSTEM_CONFIG_REDEEM_ENABLE;
    /**
     * 默认启用兑换码功能
     */
    public static boolean SYSTEM_CONFIG_REDEEM_ENABLE_DEFAULT_VALUE = true;
    /**
     * 兑换码默认过期时长，单位为秒
     */
    public static String SYSTEM_CONFIG_REDEEM_DEFAULT_EXPIRE_DURATION;
    /**
     * 默认过期时长为2小时
     */
    public static int SYSTEM_CONFIG_REDEEM_DEFAULT_EXPIRE_DURATION_DEFAULT_VALUE = 7200;
    /**
     * 兑换码支持的业务类型列表，使用换行符分割
     */
    public static String SYSTEM_CONFIG_REDEEM_TYPE_LIST;
    /**
     * 默认没有任何业务类型
     */
    public static String SYSTEM_CONFIG_REDEEM_TYPE_LIST_DEFAULT_VALUE = "";
    /**
     * 文章栏目分类
     */
    public static String SYSTEM_CONFIG_ARTICLE_CHANNEL_CATALOG = "";
}
