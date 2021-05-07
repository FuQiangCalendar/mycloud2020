package com.at.guigu.utils;

import com.at.guigu.utils.dto.News;
import com.at.guigu.utils.dto.NewsMassage;
import com.at.guigu.utils.dto.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil{
    public static final String MESSAGE_TEXT="text";  //文本
    public static final String MESSAGE_EVENT="event";  //事件
    public static final String MESSAGE_SUBSCRIBE="subscribe";
    public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
    public static final String MESSAGE_NEWS = "news";  //新闻

    public static Map<String,String> xmlToMap(HttpServletRequest req) throws IOException {
        Map<String,String> map=new HashMap<String, String>();
        InputStream in=req.getInputStream();
        try {
            SAXReader reader=new SAXReader();
            Document doc=reader.read(in);
            Element root=doc.getRootElement();
            List<Element> list=root.elements();
            for (Element element:list) {
                map.put(element.getName(),element.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            in.close();
        }
        return map;
    }
    public static String textMessageToXml(TextMessage textMessage){
        XStream xStream=new XStream();
        xStream.alias("xml",textMessage.getClass());
        return xStream.toXML(textMessage);
    }
    public static String menuText(){
        StringBuffer sb=new StringBuffer();
        sb.append("哈哈，王者之峰回來了，有事沒事來看下哦。\n");
        sb.append("沒事可以看我的博客：http://blog.csdn.net/qq_23473123\n");
        sb.append("欢迎回复文字。\n");
        sb.append("1:峰哥很帅。\n");
        sb.append("2:峰哥很帅。\n");
        sb.append("3:别说了。\n");
        return sb.toString();
    }
    public static String returnText(String ToUserName,String FromUserName,String content){
        String message=null;
        TextMessage text=new TextMessage();
        text.setFromUserName(ToUserName);
        text.setToUserName(FromUserName);
        text.setCreateTime(new Date().getTime());
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setContent(content);
        message=MessageUtil.textMessageToXml(text);
        return message;
    }

    public static String returnNews(String ToUserName,String FromUserName,int     ArticleCount,List<News> newsList){
        String message=null;
        NewsMassage news=new NewsMassage();
        news.setToUserName(FromUserName);
        news.setFromUserName(ToUserName);
        news.setCreateTime(new Date().getTime());
        news.setMsgType(MessageUtil.MESSAGE_NEWS);
        news.setArticleCount(ArticleCount);
        news.setArticles(newsList);
        message=MessageUtil.newsMessageToXml(news);
        return message;
    }
    public static String newsMessageToXml(NewsMassage newsMessage){
        XStream xStream=new XStream();
        xStream.alias("xml",newsMessage.getClass());
        xStream.alias("item",new News().getClass());
        return xStream.toXML(newsMessage);
    }

    public static void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        Map<String,String> map=MessageUtil.xmlToMap(req);
        String ToUserName=map.get("ToUserName");
        String FromUserName=map.get("FromUserName");
        String MsgType=map.get("MsgType");
        String Content=map.get("Content");
        String message=null;
        PrintWriter out=resp.getWriter();
        if(MessageUtil.MESSAGE_TEXT.equals(MsgType)){
            if(Content.equals("1")){
                message=MessageUtil.returnText(ToUserName,FromUserName,"峰哥很帅");
            }else if(Content.equals("2")){
                message=MessageUtil.returnText(ToUserName,FromUserName,"峰哥很帅");
            }else if(Content.equals("3")){
                message=MessageUtil.returnText(ToUserName,FromUserName,"别说了");
            }else{
                message=MessageUtil.returnText(ToUserName,FromUserName,"呵呵");
            }

        }else if(MessageUtil.MESSAGE_EVENT.equals(MsgType)){
            String eventType=map.get("Event");
            if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
                message=MessageUtil.returnText(ToUserName,FromUserName,MessageUtil.menuText());
            }
        }
        out.print(message);
        out.close();
    }
}