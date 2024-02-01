package com.bulade.donor.framework.biz.operatelog.core.util;

import com.bulade.donor.framework.biz.operatelog.core.aop.OperateLogAspect;

/**
 * 操作日志工具类
 * 目前主要的作用，是提供给业务代码，记录操作明细和拓展字段
 */
public class OperateLogUtils {

    private OperateLogUtils(){}

    public static void setContent(String content) {
        OperateLogAspect.setContent(content);
    }

    public static void addExt(String key, Object value) {
        OperateLogAspect.addExt(key, value);
    }

}
