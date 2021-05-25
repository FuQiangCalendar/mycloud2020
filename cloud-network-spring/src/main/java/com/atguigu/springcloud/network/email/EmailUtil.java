package com.atguigu.springcloud.network.email;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.*;
import java.util.Properties;

/**
 * @Package com.atguigu.springcloud.network.email
 * @ClassName EmailUtil
 * @Description 邮件工具类
 *
 * 准备SMTP登录信息
 * 假设我们准备使用自己的邮件地址me@example.com给小明发送邮件，已知小明的邮件地址是xiaoming@somewhere.com，发送邮件前，我们首先要确定作为MTA的邮件服务器地址和端口号。邮件服务器地址通常是smtp.example.com，端口号由邮件服务商确定使用25、465还是587。以下是一些常用邮件服务商的SMTP信息：
 *
 * QQ邮箱：SMTP服务器是smtp.qq.com，端口是465/587；
 * 163邮箱：SMTP服务器是smtp.163.com，端口是465；
 * Gmail邮箱：SMTP服务器是smtp.gmail.com，端口是465/587。
 * 有了SMTP服务器的域名和端口号，我们还需要SMTP服务器的登录信息，通常是使用自己的邮件地址作为用户名，登录口令是用户口令或者一个独立设置的SMTP口令。
 *
 * 我们来看看如何使用JavaMail发送邮件。
 * 首先，我们需要创建一个Maven工程，并把JavaMail相关的两个依赖加入进来：
 *
 * <dependencies>
 *     <dependency>
 *         <groupId>javax.mail</groupId>
 *         <artifactId>javax.mail-api</artifactId>
 *         <version>1.6.2</version>
 *     </dependency>
 *     <dependency>
 *         <groupId>com.sun.mail</groupId>
 *         <artifactId>javax.mail</artifactId>
 *         <version>1.6.2</version>
 *     </dependency>
 *     ...
 *
 * 填入真实的地址，运行上述代码，我们可以在控制台看到JavaMail打印的调试信息：
 *
 * 这是JavaMail打印的调试信息:
 * DEBUG: setDebug: JavaMail version 1.6.2
 * DEBUG: getProvider() returning javax.mail.Provider[TRANSPORT,smtp,com.sun.mail.smtp.SMTPTransport,Oracle]
 * DEBUG SMTP: need username and password for authentication
 * DEBUG SMTP: protocolConnect returning false, host=smtp.office365.com, ...
 * DEBUG SMTP: useEhlo true, useAuth true
 * 开始尝试连接smtp.office365.com:
 * DEBUG SMTP: trying to connect to host "smtp.office365.com", port 587, ...
 * DEBUG SMTP: connected to host "smtp.office365.com", port: 587
 * 发送命令EHLO:
 * EHLO localhost
 * SMTP服务器响应250:
 * 250-SG3P274CA0024.outlook.office365.com Hello
 * 250-SIZE 157286400
 * ...
 * DEBUG SMTP: Found extension "SIZE", arg "157286400"
 * 发送命令STARTTLS:
 * STARTTLS
 * SMTP服务器响应220:
 * 220 2.0.0 SMTP server ready
 * EHLO localhost
 * 250-SG3P274CA0024.outlook.office365.com Hello [111.196.164.63]
 * 250-SIZE 157286400
 * 250-PIPELINING
 * 250-...
 * DEBUG SMTP: Found extension "SIZE", arg "157286400"
 * ...
 * 尝试登录:
 * DEBUG SMTP: protocolConnect login, host=smtp.office365.com, user=********, password=********
 * DEBUG SMTP: Attempt to authenticate using mechanisms: LOGIN PLAIN DIGEST-MD5 NTLM XOAUTH2
 * DEBUG SMTP: Using mechanism LOGIN
 * DEBUG SMTP: AUTH LOGIN command trace suppressed
 * 登录成功:
 * DEBUG SMTP: AUTH LOGIN succeeded
 * DEBUG SMTP: use8bit false
 * 开发发送邮件，设置FROM:
 * MAIL FROM:<********@outlook.com>
 * 250 2.1.0 Sender OK
 * 设置TO:
 * RCPT TO:<********@sina.com>
 * 250 2.1.5 Recipient OK
 * 发送邮件数据:
 * DATA
 * 服务器响应354:
 * 354 Start mail input; end with <CRLF>.<CRLF>
 * 真正的邮件数据:
 * Date: Mon, 2 Dec 2019 09:37:52 +0800 (CST)
 * From: ********@outlook.com
 * To: ********001@sina.com
 * Message-ID: <1617791695.0.1575250672483@localhost>
 * 邮件主题是编码后的文本:
 * Subject: =?UTF-8?Q?JavaMail=E9=82=AE=E4=BB=B6?=
 * MIME-Version: 1.0
 * Content-Type: text/plain; charset=UTF-8
 * Content-Transfer-Encoding: base64
 *
 * 邮件正文是Base64编码的文本:
 * SGVsbG8sIOi/meaYr+S4gOWwgeadpeiHqmphdmFtYWls55qE6YKu5Lu277yB
 * .
 * 邮件数据发送完成后，以\r\n.\r\n结束，服务器响应250表示发送成功:
 * 250 2.0.0 OK <HK0PR03MB4961.apcprd03.prod.outlook.com> [Hostname=HK0PR03MB4961.apcprd03.prod.outlook.com]
 * DEBUG SMTP: message successfully delivered to mail server
 * 发送QUIT命令:
 * QUIT
 * 服务器响应221结束TCP连接:
 * 221 2.0.0 Service closing transmission channel
 * 从上面的调试信息可以看出，SMTP协议是一个请求-响应协议，客户端总是发送命令，然后等待服务器响应。服务器响应总是以数字开头，后面的信息才是用于调试的文本。这些响应码已经被定义在SMTP协议中了，查看具体的响应码就可以知道出错原因。
 *
 * 如果一切顺利，对方将收到一封文本格式的电子邮件：
 * javamail-text
 *
 * 发送HTML邮件
 * 发送HTML邮件和文本邮件是类似的，只需要把：
 * message.setText(body, "UTF-8");
 * 改为：
 *
 * message.setText(body, "UTF-8", "html");
 * 传入的body是类似<h1>Hello</h1><p>Hi, xxx</p>这样的HTML字符串即可。
 *
 * HTML邮件可以在邮件客户端直接显示为网页格式：
 *
 * 发送附件
 * 要在电子邮件中携带附件，我们就不能直接调用message.setText()方法，而是要构造一个Multipart对象：
 *
 * Multipart multipart = new MimeMultipart();
 * // 添加text:
 * BodyPart textpart = new MimeBodyPart();
 * textpart.setContent(body, "text/html;charset=utf-8");
 * multipart.addBodyPart(textpart);
 * // 添加image:
 * BodyPart imagepart = new MimeBodyPart();
 * imagepart.setFileName(fileName);
 * imagepart.setDataHandler(new DataHandler(new ByteArrayDataSource(input, "application/octet-stream")));
 * multipart.addBodyPart(imagepart);
 * // 设置邮件内容为multipart:
 * message.setContent(multipart);
 * 一个Multipart对象可以添加若干个BodyPart，其中第一个BodyPart是文本，即邮件正文，后面的BodyPart是附件。BodyPart依靠setContent()决定添加的内容，如果添加文本，用setContent("...", "text/plain;charset=utf-8")添加纯文本，或者用setContent("...", "text/html;charset=utf-8")添加HTML文本。如果添加附件，需要设置文件名（不一定和真实文件名一致），并且添加一个DataHandler()，传入文件的MIME类型。二进制文件可以用application/octet-stream，Word文档则是application/msword。
 *
 * 最后，通过setContent()把Multipart添加到Message中，即可发送。
 *
 * 带附件的邮件在客户端会被提示下载：
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/25 9:33
 * @Version 1.0
 **/
public class EmailUtil {


    /**
    * @Description : 发送邮件
     * 发送邮件时，我们需要构造一个Message对象，然后调用Transport.send(Message)即可完成发送：
    * 绝大多数邮件服务器要求发送方地址和登录用户名必须一致，否则发送将失败。
    * @Param []
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/25 9:57
    */
    public static void sendEmail () throws MessagingException {
        // 服务器地址:
        String smtp = "smtp.office365.com";
        // 登录用户名:
        String username = "jxsmtp101@outlook.com";
        // 登录口令:
        String password = "********";
        //获取邮件服务器的连接
        Session session = getSession(smtp, username, password, 587, true, true);

        MimeMessage message = new MimeMessage(session);
        // 设置发送方地址:
        message.setFrom(new InternetAddress("me@example.com"));
        // 设置接收方地址:
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("xiaoming@somewhere.com"));
        // 设置邮件主题:
        message.setSubject("Hello", "UTF-8");
        // 设置邮件正文:
        message.setText("Hi Xiaoming...", "UTF-8");
        // 发送:
        Transport.send(message);
    }

    /**
    * @Description : 发送附件
     *
     * 一个Multipart对象可以添加若干个BodyPart，其中第一个BodyPart是文本，即邮件正文，后面的BodyPart是附件。BodyPart依靠setContent()决定添加的内容，如果添加文本，用setContent("...", "text/plain;charset=utf-8")添加纯文本，或者用setContent("...", "text/html;charset=utf-8")添加HTML文本。如果添加附件，需要设置文件名（不一定和真实文件名一致），并且添加一个DataHandler()，传入文件的MIME类型。二进制文件可以用application/octet-stream，Word文档则是application/msword。
     * 最后，通过setContent()把Multipart添加到Message中，即可发送。
    *
    * @Param []
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/25 14:15
    */
    public static void sendMultipart () throws MessagingException, IOException {
        // 服务器地址:
        String smtp = "smtp.office365.com";
        // 登录用户名:
        String username = "jxsmtp101@outlook.com";
        // 登录口令:
        String password = "********";
        //获取邮件服务器的连接
        Session session = getSession(smtp, username, password, 587, true, true);

        Multipart multipart = new MimeMultipart();
        // 添加text:
        BodyPart textpart = new MimeBodyPart();
        File file = new File("C:\\Users\\Administrator\\Desktop\\开发日志\\测试\\申请人统计分析（线上）.xls");
        InputStream input = new FileInputStream(file);
        String fileName = file.getName();
        String body = null;
        textpart.setContent(body, "text/html;charset=utf-8");
        multipart.addBodyPart(textpart);
        // 添加image:
        BodyPart imagepart = new MimeBodyPart();
        imagepart.setFileName(fileName);
        imagepart.setDataHandler(new DataHandler(new ByteArrayDataSource(input, "application/octet-stream")));
        multipart.addBodyPart(imagepart);

        MimeMessage message = new MimeMessage(session);
        // 设置发送方地址:
        message.setFrom(new InternetAddress("me@example.com"));
        // 设置接收方地址:
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("xiaoming@somewhere.com"));
        // 设置邮件主题:
        message.setSubject("Hello", "UTF-8");
        // 设置邮件正文:
        message.setText("Hi Xiaoming...", "UTF-8");
        // 设置邮件内容为multipart:
        message.setContent(multipart);
        // 发送:
        Transport.send(message);
    }


    /**
    * @Description : 发送内嵌图片的HTML邮件
     * 有些童鞋可能注意到，HTML邮件中可以内嵌图片，这是怎么做到的？
     *
     * 如果给一个<img src="http://example.com/test.jpg">，这样的外部图片链接通常会被邮件客户端过滤，并提示用户显示图片并不安全。只有内嵌的图片才能正常在邮件中显示。
     * 内嵌图片实际上也是一个附件，即邮件本身也是Multipart，但需要做一点额外的处理：
    *
     * 在HTML邮件中引用图片时，需要设定一个ID，用类似<img src=\"cid:img01\">引用，然后，在添加图片作为BodyPart时，除了要正确设置MIME类型（根据图片类型使用image/jpeg或image/png），还需要设置一个Header：
     * imagepart.setHeader("Content-ID", "<img01>");
     * 这个ID和HTML中引用的ID对应起来，邮件客户端就可以正常显示内嵌图片：
     *
    * @Param []
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/25 14:18
    */
    public static void sendInlineHtml () throws MessagingException, IOException {
        // 服务器地址:
        String smtp = "smtp.office365.com";
        // 登录用户名:
        String username = "jxsmtp101@outlook.com";
        // 登录口令:
        String password = "********";
        //获取邮件服务器的连接
        Session session = getSession(smtp, username, password, 587, true, true);

        File file = new File("C:\\Users\\Administrator\\Desktop\\开发日志\\测试\\copy.jpg");
        InputStream input = new FileInputStream(file);
        String fileName = file.getName();

        Multipart multipart = new MimeMultipart();
        // 添加text:
        BodyPart textpart = new MimeBodyPart();
        textpart.setContent("<h1>Hello</h1><p><img src=\"cid:img01\"></p>", "text/html;charset=utf-8");
        multipart.addBodyPart(textpart);
        // 添加image:
        BodyPart imagepart = new MimeBodyPart();
        imagepart.setFileName(fileName);
        imagepart.setDataHandler(new DataHandler(new ByteArrayDataSource(input, "image/jpeg")));
        // 与HTML的<img src="cid:img01">关联:
        imagepart.setHeader("Content-ID", "<img01>");
        multipart.addBodyPart(imagepart);

        MimeMessage message = new MimeMessage(session);
        // 设置发送方地址:
        message.setFrom(new InternetAddress("me@example.com"));
        // 设置接收方地址:
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("xiaoming@somewhere.com"));
        // 设置邮件主题:
        message.setSubject("Hello", "UTF-8");
        // 设置邮件正文:
        message.setText("Hi Xiaoming...", "UTF-8");
        // 设置邮件内容为multipart:
        message.setContent(multipart);
        // 发送:
        Transport.send(message);
    }


    /**
    * @Description : 获取邮件服务器session
    *
    * @Param [
     * smtp, //邮件服务器地址
     * username,//登录用户名
     * password, //登录口令(授权码)
     * port, //主机端口号
     * authenFlag, //是否需要用户认证
     * tlsFlag  //启用TLS加密
     * ]
    * @return:javax.mail.Session
    * @Author:FuQiangCalendar
    * @Date: 2021/5/25 9:44
    */
    public static Session getSession (String smtp, String username, String password, int port, boolean authenFlag, boolean tlsFlag) {
        // 连接到SMTP服务器587端口:
        Properties props = new Properties();
        props.put("mail.smtp.host", smtp); // SMTP主机名
        props.put("mail.smtp.port", String.valueOf(port)); // 主机端口号
        props.put("mail.smtp.auth", String.valueOf(authenFlag)); // 是否需要用户认证
        props.put("mail.smtp.starttls.enable", String.valueOf(tlsFlag)); // 启用TLS加密
        // 获取Session实例:
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        // 设置debug模式便于调试:
        session.setDebug(true);
        return session;
    }


    /**
    * 然后，我们通过JavaMail API连接到SMTP服务器上：
     * 以587端口为例，连接SMTP服务器时，需要准备一个Properties对象，填入相关信息。最后获取Session实例时，如果服务器需要认证，还需要传入一个Authenticator对象，并返回指定的用户名和口令。
     * 当我们获取到Session实例后，打开调试模式可以看到SMTP通信的详细内容，便于调试。
    *
    * @Param []
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/25 9:39
    */
    public static void test1 () {
        // 服务器地址:
        String smtp = "smtp.office365.com";
        // 登录用户名:
        String username = "jxsmtp101@outlook.com";
        // 登录口令:
        String password = "********";
        // 连接到SMTP服务器587端口:
        Properties props = new Properties();
        props.put("mail.smtp.host", smtp); // SMTP主机名
        props.put("mail.smtp.port", "587"); // 主机端口号
        props.put("mail.smtp.auth", "true"); // 是否需要用户认证
        props.put("mail.smtp.starttls.enable", "true"); // 启用TLS加密
        // 获取Session实例:
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        // 设置debug模式便于调试:
        session.setDebug(true);
    }
}
