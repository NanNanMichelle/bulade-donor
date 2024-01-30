package com.bulade.donor.infra.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiAccessLog {
    /**
     * 编号
     */
    private Long id;

    private String traceId;
    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 应用名
     *
     * 目前读取 `spring.application.name` 配置项
     */
    private String applicationName;

    // ========== 请求相关字段 ==========

    /**
     * 请求方法名
     */
    private String requestMethod;

    /**
     * 访问地址
     */
    private String requestUrl;

    /**
     * 请求参数
     *
     * query: Query String
     * body: Quest Body
     */
    private String requestParams;

    /**
     * 用户 IP
     */
    private String userIp;

    /**
     * 浏览器 UA
     */
    private String userAgent;

    // ========== 执行相关字段 ==========

    /**
     * 开始请求时间
     */
    private LocalDateTime beginTime;

    /**
     * 结束请求时间
     */
    private LocalDateTime endTime;

    /**
     * 执行时长，单位：毫秒
     */
    private Integer duration;

    /**
     * 结果码

     */
    private Integer resultCode;

    /**
     * 结果提示
     */
    private String resultMsg;
}
