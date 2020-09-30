package com.atguigu.springcloud.config.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

public class ParamsFilter implements Filter {
	
	public static final String PARAM_NAME_EXCLUSIONS = "exclusions";
    public static final String SEPARATOR = ",";
    private Set<String> excludesUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	String param = filterConfig.getInitParameter(PARAM_NAME_EXCLUSIONS);
        if (param != null && param.trim().length() != 0) {
            this.excludesUrls = new HashSet(Arrays.asList(param.split(SEPARATOR)));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();
        
        if (this.isExclusion(requestURI)) {
            // 不过滤
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // 过滤
            ParamsRequestWrapper requestWrapper = new ParamsRequestWrapper(httpRequest);
            filterChain.doFilter(requestWrapper, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
    
    public boolean isExclusion(String requestURI) {

        if (this.excludesUrls == null) {
            return false;
        }

        for (String url : this.excludesUrls) {
        	if (url.indexOf("/*") != -1) {
        		url = url.substring(0, url.lastIndexOf("/*"));
        		if (requestURI.indexOf(url) == 0) {
        			return true;
        		}else {
        			return false;
        		}
        	}
            if (url.equals(requestURI)) {
                return true;
            }
        }
        return false;
    }


}