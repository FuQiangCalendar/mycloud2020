package com.atguigu.springcloud.config.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @ClassName:	ParamsRequestWrapper
 * @Description: 对请求参数进行过滤
 * @author:	FuQiang
 * @date:	2020年9月30日 上午10:46:01
 */
public class ParamsRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params = new HashMap<>();
    private static final String ENCODING = "UTF-8";
    private static final String CLASSTYPE = "java.lang.String";

    public ParamsRequestWrapper(HttpServletRequest request) {
        super(request);
        // 将参数表，赋予给当前的Map以便于持有request中的参数
        Map<String, String[]> requestMap = request.getParameterMap();
        System.out.println("转化前参数：" + JSON.toJSONString(requestMap));
        this.params.putAll(requestMap);
        this.modifyParameters();
        System.out.println("转化后参数：" + JSON.toJSONString(params));
        request.setAttribute("parameterMap", this.params);
    }


    /**
     * 重写getInputStream方法  post请求参数必须通过流才能获取到值
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {

        ServletInputStream stream = super.getInputStream();

        //非json类型，直接返回
        if (!super.getHeader(HttpHeaders.CONTENT_TYPE).equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            return stream;
        }
        String json = IOUtils.toString(stream, ENCODING);

        if (StringUtils.isBlank(json)) {
            return stream;
        }

        System.out.println("转化前参数：" + json);
        Map<String, Object> map = modifyParams(json);
        System.out.println("转化后参数：" + JSON.toJSONString(map));

        ByteArrayInputStream bis = new ByteArrayInputStream(JSON.toJSONString(map).getBytes(ENCODING));

        return new ParamsServletInputStream(bis);
    }

    private static Map<String, Object> modifyParams(String json) {

        Map<String, Object> params = JSON.parseObject(json);
        Map<String, Object> maps = new HashMap<>(params.size());
        for (String key : params.keySet()) {
            Object value = getValue(params.get(key));
            maps.put(key, value);
        }
        return maps;
    }

    private static Object getValue(Object obj) {

        if (obj == null) {
            return null;
        }
        String type = obj.getClass().getName();
        // 对字符串的处理
        if (CLASSTYPE.equals(type)) {
            obj = obj.toString().trim();
        }
        return obj;
    }

    /**
     * 将parameter的值去除空格后重写回去
     */
    private void modifyParameters() {
        Set<String> set = params.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String[] values = params.get(key);
            values[0] = values[0].trim();
            params.put(key, values);
        }
    }

    /**
     * 重写getParameter 参数从当前类中的map获取
     */
    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }
    
    /**
     * 
     * @ClassName:	ParamsServletInputStream
     * @Description: 请求参数输入流
     * @author:	FuQiang
     * @date:	2020年9月30日 上午10:45:44
     */
    public class ParamsServletInputStream extends ServletInputStream {

        private ByteArrayInputStream bis;

        public ParamsServletInputStream(ByteArrayInputStream bis) {
            this.bis = bis;
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return bis.read();
        }

    }
}