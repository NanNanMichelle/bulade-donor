package com.bulade.donor.framework.xss.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XssFilter implements Filter {

    //是否过滤富文本内容
    private static boolean IS_INCLUDE_RICH_TEXT = true;

    public List<String> excludes = new ArrayList<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (handleExcludeURL(req, resp)) {
            filterChain.doFilter(request, response);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request, IS_INCLUDE_RICH_TEXT);
        filterChain.doFilter(xssRequest, response);
    }

    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
        if (excludes == null || excludes.isEmpty()) {
            return false;
        }
        String url = request.getServletPath();
        for (String pattern : excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String isIncludeRichText = filterConfig.getInitParameter("isIncludeRichText");
        if (StringUtils.isNotBlank(isIncludeRichText)) {
            IS_INCLUDE_RICH_TEXT = BooleanUtils.toBoolean(isIncludeRichText);
        }
        String temp = filterConfig.getInitParameter("excludes");
        if (temp != null) {
            String[] url = temp.split(",");
            excludes.addAll(Arrays.asList(url));
        }
    }

    @Override
    public void destroy() {
    }

}

