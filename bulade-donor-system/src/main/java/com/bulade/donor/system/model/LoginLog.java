package com.bulade.donor.system.model;

import lombok.Data;

@Data
public class LoginLog {

    /**
     * 日志主键
     */
    private Long id;

    /**
     * 日志类型
     */
    private Integer logType;

    /**
     * 链路追踪编号
     */
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
     * 用户账号
     * <p>
     * 冗余，因为账号可以变更
     */
    private String username;

    /**
     * 登录结果
     */
    private Integer result;

    /**
     * 用户 IP
     */
    private String userIp;

    /**
     * 浏览器 UA
     */
    private String userAgent;


}
