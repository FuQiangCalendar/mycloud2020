package com.atguigu.springcloud.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
@Slf4j
public class SendEmailUtil {
    public static  void sendHtmlAndAttachment(String host, String fromEmail, String password, String recieveEmail, String subject, String text, Map<String, File> fileMap) throws Exception {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.163.com");
        mailSender.setHost(host);
//		jms.setPort(port);
//        mailSender.setUsername("15556074179@163.com");
        mailSender.setUsername(fromEmail);

//        mailSender.setPassword("ww010714");
        mailSender.setPassword(password);

        mailSender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.auth", "true");
        mailSender.setJavaMailProperties(p);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom("15556074179@163.com");
        helper.setFrom(fromEmail);

//            helper.setTo("2362410870@qq.com");
        helper.setTo(recieveEmail);

        helper.setSubject(subject);
        helper.setText(text, true);

        if (fileMap != null) {
            for (String fileName : fileMap.keySet()) {
                File file = fileMap.get(fileName);
                helper.addAttachment(fileName, file);
            }
        }
        mailSender.send(message);
        log.info("邮件发送成功！");
    }

    public static  void sendHtmlAndAttachment1(String host, String fromEmail, String password, String recieveEmail, String subject, String text, Map<String, File> fileMap) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.163.com");
        mailSender.setHost(host);
//		jms.setPort(port);
//        mailSender.setUsername("15556074179@163.com");
        mailSender.setUsername(fromEmail);

//        mailSender.setPassword("ww010714");
        mailSender.setPassword(password);

        mailSender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.auth", "true");
        mailSender.setJavaMailProperties(p);
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom("15556074179@163.com");
            helper.setFrom(fromEmail);

            helper.setTo("2362410870@qq.com");
//            helper.setTo(recieveEmail);

            helper.setSubject(subject);
            helper.setText(text, true);

            if (fileMap != null) {
                for (String fileName : fileMap.keySet()) {
                    File file = fileMap.get(fileName);
                    helper.addAttachment(fileName, file);
                }
            }
            mailSender.send(message);
            log.info("邮件发送成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        sendHtmlAndAttachment1("smtp.126.com", "kuaiyiquan@126.com", "9162kyqeac", "2362410870@qq.com", "主题", "内容", null);
        String providerId = 1 + "";
        boolean contains = Arrays.asList("1".split(",")).contains(providerId);
        System.out.println(contains);

    }
}
