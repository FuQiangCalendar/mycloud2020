package com.atguigu.springcloud.utils;

import com.atguigu.springcloud.utils.dto.AccessToken;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpHostConfig;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Package com.atguigu.springcloud.utils
 * @ClassName WechatUtil
 * @Description TODO
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/6 17:40
 * @Version 1.0
 **/
@Component
@Slf4j
public class WechatUtil {
    @Autowired
    private static WxMpService wxMpService;

    public final static String UPLOAD_URL = WxMpHostConfig.API_DEFAULT_HOST_URL + "/cgi-bin/media/upload?access_token=%s&type=%s";

    public static AccessToken getAccessToken(String appid, String secret) throws Exception {
        AccessToken token = new AccessToken();
        String accessToken = wxMpService.getAccessToken();
        JSONObject jsonObject = JSONObject.fromObject(accessToken);
        if (jsonObject != null) {
            token.setAccessToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return token;
    }

    public static JSONObject doGetStr(String url) throws IOException {
        JSONObject jsonObject = null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = client.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            String result = EntityUtils.toString(httpEntity, "UTF-8");
            jsonObject = JSONObject.fromObject(result);
        }
        return jsonObject;
    }

    public static String uploadFile(String filePath, String accessToken, String type) throws Exception {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }
        if (StringUtils.isBlank(accessToken)) {
            accessToken = wxMpService.getAccessToken();
        }
//        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE".replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
        String url = String.format(UPLOAD_URL, accessToken, type);
        URL urlObj = new URL(url);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);

        //设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        //设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        //获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        //输出表头
        out.write(head);

        //文件正文部分
        //把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        //结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            //定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        JSONObject jsonObj = JSONObject.fromObject(result);
        System.out.println(jsonObj);
        String typeName = "media_id";
        if (!"image".equals(type)) {
            typeName = type + "_media_id";
        }
        String mediaId = jsonObj.getString(typeName);
        return mediaId;
    }
    public static void main(String[] args) throws Exception {
//        System.out.println("accessToken:"+getAccessToken("wx1c7356240130df4a","067707aa0936ce56160ee1a470f811b6").getAccessToken());
//        System.out.println("mediaId:"+uploadFile("C:/Users/Administrator/Pictures/huangtugaoyuan.jpg","ejn9c4ptsC0JfYY9s8duoDbUWnkLTWJdbTJuhYNgG-EOhP3YNUb6qyugdX2bNzjad0I1GuOD8_I531SwWGgWbYw1f_wF73gAlazn5qIBwInz8pCSuP13uo-OlVhkz5qqTMBdAAAKDS","image"));
        AccessToken accessToken = getAccessToken("wxf88734ba1fc9fcc0", "5bdf010835d2f53af63f99883492e41c");
        System.out.println(accessToken);
        String image = uploadFile("C:/Users/Administrator/Desktop/测试/copy.jpg", accessToken.getAccessToken(), "image");

        System.out.println("mediaId:" + image);
    }
}
