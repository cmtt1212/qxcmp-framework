package com.qxcmp.framework.message;

import com.qxcmp.framework.config.SystemConfigService;
import com.qxcmp.framework.support.ThrowingConsumer;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static com.qxcmp.framework.core.QXCMPConfiguration.*;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private SystemConfigService systemConfigService;

    private JavaMailSender javaMailSender;

    @Override
    public void send(ThrowingConsumer<MimeMessageHelper, MessagingException> consumer) throws MailException {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
            mimeMessageHelper.setFrom(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME_DEFAULT_VALUE));
            consumer.accept(mimeMessageHelper);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new MailSendException(e.getMessage(), e);
        }
    }

    @Override
    public void config() {
        JavaMailSenderImpl sender = (JavaMailSenderImpl) javaMailSender;
        sender.setProtocol("smtp");
        sender.setHost(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME_DEFAULT_VALUE));
        sender.setPort(systemConfigService.getInteger(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT_DEFAULT_VALUE));
        sender.setUsername(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME_DEFAULT_VALUE));
        sender.setPassword(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD_DEFAULT_VALUE));

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME_DEFAULT_VALUE));
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.port", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT).orElse(String.valueOf(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT_DEFAULT_VALUE)));
        properties.put("mail.smtp.socketFactory.port", sender.getPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.timeout", "5000");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        sender.setJavaMailProperties(properties);
    }
}
