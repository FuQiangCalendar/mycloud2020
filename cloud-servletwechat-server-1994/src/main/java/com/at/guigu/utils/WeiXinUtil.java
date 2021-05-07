package com.at.guigu.utils;

import com.at.guigu.utils.dto.AccessToken;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class WeiXinUtil {
    public static AccessToken getAccessToken(String appid, String secret) throws IOException {
        AccessToken token = new AccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET".replace("APPID", appid).replace("APPSECRET", secret);
        JSONObject jsonObject=doGetStr(url);
        if(jsonObject!=null){
            token.setAccessToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return token;
    }
    /*
     *get请求
     */
    public static JSONObject doGetStr(String url) throws IOException {
        JSONObject jsonObject=null;
        DefaultHttpClient client=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        HttpResponse httpResponse=client.execute(httpGet);
        HttpEntity httpEntity=httpResponse.getEntity();
        if(httpEntity!=null){
            String result= EntityUtils.toString(httpEntity,"UTF-8");
            jsonObject=JSONObject.fromObject(result);
        }
        return jsonObject;
    }
    public static void main(String[] args) throws IOException {
        System.out.println(getAccessToken("wx1c7356240130df4a","067707aa0936ce56160ee1a470f811b6").getAccessToken());
    }
}