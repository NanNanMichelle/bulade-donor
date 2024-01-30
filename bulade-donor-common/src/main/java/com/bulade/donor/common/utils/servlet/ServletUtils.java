package com.bulade.donor.common.utils.servlet;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.bulade.donor.common.utils.json.JsonUtils;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 客户端工具类
 */
public class ServletUtils {

    private ServletUtils() {

    }

    /**
     * 返回 JSON 字符串
     *
     * @param response 响应
     * @param object   对象，会序列化成 JSON 字符串
     */
    @SuppressWarnings("deprecation") // 必须使用 APPLICATION_JSON_UTF8_VALUE，否则会乱码
    public static void writeJSON(HttpServletResponse response, Object object) {
        String content = JsonUtils.toJsonString(object);
        write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    public static void write(HttpServletResponse response, String text, String contentType) {
        response.setContentType(contentType);
        Writer writer = null;

        try {
            writer = response.getWriter();
            writer.write(text);
            writer.flush();
        } catch (IOException var8) {
            throw new UtilException(var8);
        } finally {
            IoUtil.close(writer);
        }
    }

    /**
     * 返回附件
     *
     * @param response 响应
     * @param filename 文件名
     * @param content  附件内容
     */
    public static void writeAttachment(HttpServletResponse response, String filename, byte[] content)
        throws IOException {
        // 设置 header 和 contentType
        response.setHeader("Content-Disposition", "attachment;filename="
            + URLEncoder.encode(filename, StandardCharsets.UTF_8));
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        // 输出附件
        IoUtil.write(response.getOutputStream(), false, content);
    }

    /**
     * @param request 请求
     * @return ua
     */
    public static String getUserAgent(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        return ua != null ? ua : "";
    }

    /**
     * 获得请求
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static String getUserAgent() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return getUserAgent(request);
    }

    public static String getClientIP() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return getClientIPByHeader(request);
    }

    public static String getClientIP(HttpServletRequest request) {
        String[] headers = new String[]{"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP",
            "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        return getClientIPByHeader(request, headers);
    }

    public static Map<String, String[]> getParams(ServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        return Collections.unmodifiableMap(map);
    }

    public static String getClientIPByHeader(HttpServletRequest request, String... headerNames) {
        String ip;
        for (String header : headerNames) {
            ip = request.getHeader(header);
            if (!NetUtil.isUnknown(ip)) {
                return NetUtil.getMultistageReverseProxyIp(ip);
            }
        }
        ip = request.getRemoteAddr();
        return NetUtil.getMultistageReverseProxyIp(ip);
    }

    public static boolean isJsonRequest(HttpServletRequest request) {
        return CharSequenceUtil.startWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE);
    }

    public static byte[] getBodyBytes(HttpServletRequest request) {
        try {
            return IoUtil.readBytes(request.getInputStream());
        } catch (IOException var2) {
            throw new IORuntimeException(var2);
        }
    }

    public static Map<String, String> getParamMap(HttpServletRequest request) {
        var params = new HashMap<String, String>();
        for (Map.Entry<String, String[]> stringEntry : getParams(request).entrySet()) {
            params.put(stringEntry.getKey(), ArrayUtil.join((Object[]) stringEntry.getValue(), ","));
        }
        return params;
    }

    public static String getBody(HttpServletRequest request) {
        try {
            BufferedReader reader = request.getReader();
            Throwable var2 = null;

            String var3;
            try {
                var3 = IoUtil.read(reader);
            } catch (Throwable var13) {
                var2 = var13;
                throw var13;
            } finally {
                if (reader != null) {
                    if (var2 != null) {
                        try {
                            reader.close();
                        } catch (Throwable var12) {
                            var2.addSuppressed(var12);
                        }
                    } else {
                        reader.close();
                    }
                }

            }

            return var3;
        } catch (IOException var15) {
            throw new IORuntimeException(var15);
        }
    }
}
