package com.atguigu.springcloud.network.email;

/**
 * @Package com.atguigu.springcloud.network.email
 * @ClassName SendEmail
 * @Description 发送Email
 *
 * Email就是电子邮件。电子邮件的应用已经有几十年的历史了，我们熟悉的邮箱地址比如abc@example.com，邮件软件比如Outlook都是用来收发邮件的。
 * 使用Java程序也可以收发电子邮件。我们先来看一下传统的邮件是如何发送的。
 * 传统的邮件是通过邮局投递，然后从一个邮局到另一个邮局，最终到达用户的邮箱：
 *
 *            ┌──────────┐    ┌──────────┐
 *            │PostOffice│    │PostOffice│     .───.
 * ┌─────┐    ├──────────┤    ├──────────┤    (   ( )
 * │═══ ░│───>│ ┌─┐ ┌┐┌┐ │───>│ ┌─┐ ┌┐┌┐ │───> `─┬─'
 * └─────┘    │ │░│ └┘└┘ │    │ │░│ └┘└┘ │       │
 *            └─┴─┴──────┘    └─┴─┴──────┘       │
 * 电子邮件的发送过程也是类似的，只不过是电子邮件是从用户电脑的邮件软件，例如Outlook，发送到邮件服务器上，可能经过若干个邮件服务器的中转，最终到达对方邮件服务器上，收件方就可以用软件接收邮件：
 *
 *              ┌─────────┐    ┌─────────┐    ┌─────────┐
 *              │░░░░░░░░░│    │░░░░░░░░░│    │░░░░░░░░░│
 * ┌───────┐    ├─────────┤    ├─────────┤    ├─────────┤    ┌───────┐
 * │░░░░░░░│    │░░░░░░░░░│    │░░░░░░░░░│    │░░░░░░░░░│    │░░░░░░░│
 * ├───────┤    ├─────────┤    ├─────────┤    ├─────────┤    ├───────┤
 * │       │───>│O ░░░░░░░│───>│O ░░░░░░░│───>│O ░░░░░░░│<───│       │
 * └───────┘    └─────────┘    └─────────┘    └─────────┘    └───────┘
 *    MUA           MTA            MTA            MDA           MUA
 * 我们把类似Outlook这样的邮件软件称为MUA：Mail User Agent，意思是给用户服务的邮件代理；邮件服务器则称为MTA：Mail Transfer Agent，意思是邮件中转的代理；最终到达的邮件服务器称为MDA：Mail Delivery Agent，意思是邮件到达的代理。电子邮件一旦到达MDA，就不再动了。实际上，电子邮件通常就存储在MDA服务器的硬盘上，然后等收件人通过软件或者登陆浏览器查看邮件。
 * MTA和MDA这样的服务器软件通常是现成的，我们不关心这些服务器内部是如何运行的。要发送邮件，我们关心的是如何编写一个MUA的软件，把邮件发送到MTA上。
 * MUA到MTA发送邮件的协议就是SMTP协议，它是Simple Mail Transport Protocol的缩写，使用标准端口25，也可以使用加密端口465或587。
 * SMTP协议是一个建立在TCP之上的协议，任何程序发送邮件都必须遵守SMTP协议。使用Java程序发送邮件时，我们无需关心SMTP协议的底层原理，只需要使用JavaMail这个标准API就可以直接发送邮件。
 *
 * 常见问题:
 * 如果用户名或口令错误，会导致535登录失败：
 * DEBUG SMTP: AUTH LOGIN failed
 * Exception in thread "main" javax.mail.AuthenticationFailedException: 535 5.7.3 Authentication unsuccessful [HK0PR03CA0105.apcprd03.prod.outlook.com]
 * 如果登录用户和发件人不一致，会导致554拒绝发送错误：
 *
 * DEBUG SMTP: MessagingException while sending, THROW:
 * com.sun.mail.smtp.SMTPSendFailedException: 554 5.2.0 STOREDRV.Submission.Exception:SendAsDeniedException.MapiExceptionSendAsDenied;
 * 有些时候，如果邮件主题和正文过于简单，会导致554被识别为垃圾邮件的错误：
 *
 * DEBUG SMTP: MessagingException while sending, THROW:
 * com.sun.mail.smtp.SMTPSendFailedException: 554 DT:SPM
 *
 * 小结：
 * 使用JavaMail API发送邮件本质上是一个MUA软件通过SMTP协议发送邮件至MTA服务器；
 * 打开调试模式可以看到详细的SMTP交互信息；
 * 某些邮件服务商需要开启SMTP，并需要独立的SMTP登录密码。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/25 9:26
 * @Version 1.0
 **/
public class SendEmail {
    //详见EmailUtil
}
