package com.atguigu.springcloud.network.email;

import com.sun.mail.pop3.POP3SSLStore;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.util.Properties;

/**
 * @Package com.atguigu.springcloud.network.email
 * @ClassName ReceiveEmail
 * @Description 接收Email
 *
 * 发送Email的过程我们在上一节已经讲过了，客户端总是通过SMTP协议把邮件发送给MTA。
 * 接收Email则相反，因为邮件最终到达收件人的MDA服务器，所以，接收邮件是收件人用自己的客户端把邮件从MDA服务器上抓取到本地的过程。
 * 接收邮件使用最广泛的协议是POP3：Post Office Protocol version 3，它也是一个建立在TCP连接之上的协议。POP3服务器的标准端口是110，如果整个会话需要加密，那么使用加密端口995。
 * 另一种接收邮件的协议是IMAP：Internet Mail Access Protocol，它使用标准端口143和加密端口993。IMAP和POP3的主要区别是，IMAP协议在本地的所有操作都会自动同步到服务器上，并且，IMAP可以允许用户在邮件服务器的收件箱中创建文件夹。
 * JavaMail也提供了IMAP协议的支持。因为POP3和IMAP的使用方式非常类似，因此我们只介绍POP3的用法。
 * 使用POP3收取Email时，我们无需关心POP3协议底层，因为JavaMail提供了高层接口。首先需要连接到Store对象
 *
 * 小结：
 * 使用Java接收Email时，可以用POP3协议或IMAP协议。
 * 使用POP3协议时，需要用Maven引入JavaMail依赖，并确定POP3服务器的域名／端口／是否使用SSL等，然后，调用相关API接收Email。
 * 设置debug模式可以查看通信详细内容，便于排查错误。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/25 14:25
 * @Version 1.0
 **/
@Slf4j
public class ReceiveEmail {

    /**
    * @Description : 使用POP3收取Email时，我们无需关心POP3协议底层，因为JavaMail提供了高层接口。首先需要连接到Store对象
    *
    * @Param []
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/25 14:36
    */
    public static Store getStore () throws MessagingException {
        // 准备登录信息:
        String host = "pop3.example.com";
        int port = 995;
        String username = "bob@example.com";
        String password = "password";

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3"); // 协议名称
        props.setProperty("mail.pop3.host", host);// POP3主机名
        props.setProperty("mail.pop3.port", String.valueOf(port)); // 端口号
        // 启动SSL:
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", String.valueOf(port));

        // 连接到Store:
        URLName url = new URLName("pop3", host, port, "", username, password);
        Session session = Session.getInstance(props, null);
        session.setDebug(true); // 显示调试信息
        Store store = new POP3SSLStore(session, url);

        store.connect();

        return store;
    }

    /**
    * @Description : 一个Store对象表示整个邮箱的存储，要收取邮件，我们需要通过Store访问指定的Folder（文件夹），通常是INBOX表示收件箱：
    *
    * @Param []
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/25 15:10
    */
    public static void readEmail (Store store) throws MessagingException, IOException {
        // 获取收件箱:
        Folder folder = store.getFolder("INBOX");
        // 以读写方式打开:
        folder.open(Folder.READ_WRITE);
        // 打印邮件总数/新邮件数量/未读数量/已删除数量:
        System.out.println("Total messages: " + folder.getMessageCount());
        System.out.println("New messages: " + folder.getNewMessageCount());
        System.out.println("Unread messages: " + folder.getUnreadMessageCount());
        System.out.println("Deleted messages: " + folder.getDeletedMessageCount());
        // 获取每一封邮件:
        Message[] messages = folder.getMessages();
        for (Message message : messages) {
            // 打印每一封邮件:
            printMessage((MimeMessage) message);
        }

        //最后记得关闭Folder和Store：
        folder.close(true); // 传入true表示删除操作会同步到服务器上（即删除服务器收件箱的邮件）
        store.close();
    }

    /**
    * @Description : 当我们获取到一个Message对象时，可以强制转型为MimeMessage，然后打印出邮件主题、发件人、收件人等信息
    * @Param [msg]
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/25 15:14
    */
    public static void printMessage(MimeMessage msg) throws IOException, MessagingException {
        // 邮件主题:
        System.out.println("Subject: " + MimeUtility.decodeText(msg.getSubject()));
        // 发件人:
        Address[] froms = msg.getFrom();
        InternetAddress address = (InternetAddress) froms[0];
        String personal = address.getPersonal();
        String from = personal == null ? address.getAddress() : (MimeUtility.decodeText(personal) + " <" + address.getAddress() + ">");
        System.out.println("From: " + from);
        // 继续打印收件人:
        String body = getBody(msg);
        log.info("邮件主体：" + body);
    }

    /**
    * @Description : 比较麻烦的是获取邮件的正文。一个MimeMessage对象也是一个Part对象，它可能只包含一个文本，也可能是一个Multipart对象，即由几个Part构成，因此，需要递归地解析出完整的正文
    * @Param [part]
    * @return:java.lang.String
    * @Author:FuQiangCalendar
    * @Date: 2021/5/25 15:15
    */
    public static String getBody(Part part) throws MessagingException, IOException {
        if (part.isMimeType("text/*")) {
            // Part是文本:
            return part.getContent().toString();
        }
        if (part.isMimeType("multipart/*")) {
            // Part是一个Multipart对象:
            Multipart multipart = (Multipart) part.getContent();
            // 循环解析每个子Part:
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String body = getBody(bodyPart);
                if (!body.isEmpty()) {
                    return body;
                }
            }
        }
        return "";
    }
}
