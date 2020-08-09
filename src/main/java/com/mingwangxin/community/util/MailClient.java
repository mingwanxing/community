package com.mingwangxin.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailClient {


    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    // JavaMailSender 也是有 spring 容器关管理的没，直接注入
    @Autowired
    private JavaMailSender mailSender;

    // 从 properties 文件中注入value
    @Value("${spring.mail.username}")
    private String from;

    /**
     *
     * @param to 发给谁
     * @param subject 邮件主题是什么
     * @param content 内容是什么
     */
    public void sendMail(String to, String subject, String content) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            // MimeMessageHelper spring 提供的帮助构建 mail 的帮助类
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            // 不加参数会认为是普通文本，文字而已，加了之后表明允许支持HTML文本的
            helper.setText(content, true);
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("发送邮件失败：" + e.getMessage());
        }
    }























}
