package com.bulade.donor.common.enums;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 系统错误
     */
    ERROR(500, "系统错误"),

    /**
     * 操作失败
     */
    FAILED(101, "操作失败"),

    /**
     * 参数错误
     */
    PARAM_ERROR(103, "参数错误"),

    /**
     * 参数错误-已存在
     */
    INVALID_PARAM_EXIST(104, "请求参数已存在"),

    /**
     * 参数错误
     */
    INVALID_PARAM_EMPTY(105, "请求参数为空"),

    /**
     * 参数错误
     */
    PARAM_TYPE_MISMATCH(106, "参数类型不匹配"),

    /**
     * 参数错误
     */
    PARAM_VALID_ERROR(107, "参数校验失败"),

    /**
     * 参数错误
     */
    ILLEGAL_REQUEST(108, "非法请求"),

    /**
     * 验证码错误
     */
    INVALID_VCODE(204, "验证码错误"),

    /**
     * 用户名或密码错误
     */
    INVALID_USERNAME_PASSWORD(205, "账号或密码错误"),

    /**
     *
     */
    INVALID_RE_PASSWORD(206, "两次输入密码不一致"),

    /**
     * 用户名或密码错误
     */
    INVALID_OLD_PASSWORD(207, "旧密码错误"),

    /**
     * 用户名重复
     */
    USERNAME_ALREADY_IN(208, "用户名已存在"),

    /**
     * 用户不存在
     */
    INVALID_USERNAME(209, "用户名不存在"),

    AUTH_LOGIN_USER_DISABLED(203, "账号被禁用"),

    /**
     * 角色不存在
     */
    INVALID_ROLE(210, "角色不存在"),

    /**
     * 角色不存在
     */
    ROLE_USED(211, "角色使用中，不可删除"),

    /**
     * 用户未登录
     */
    UNAUTHORIZED(401, "用户未登录"),

    /**
     * 没有权限
     */
    NO_PERMISSION(403, "当前用户无该接口权限"),

    NOT_FOUND(404, "请求未找到"),

    METHOD_NOT_ALLOWED(405, "请求方法不正确"),

    TOO_MANY_REQUESTS(429, "请求过于频繁，请稍后重试"),

    NOT_IMPLEMENTED(501, "功能未实现/未开启"),

    UNKNOWN(999, "未知错误"),

    // ========== 定时任务 1-001-001-000 ==========
    JOB_NOT_EXISTS(1_001_001_000, "定时任务不存在"),

    JOB_HANDLER_EXISTS(1_001_001_001, "定时任务的处理器已经存在"),

    JOB_CHANGE_STATUS_INVALID(1_001_001_002, "只允许修改为开启或者关闭状态"),

    JOB_CHANGE_STATUS_EQUALS(1_001_001_003, "定时任务已经处于该状态，无需修改"),

    JOB_UPDATE_ONLY_NORMAL_STATUS(1_001_001_004, "只有开启状态的任务，才可以修改"),

    JOB_CRON_EXPRESSION_VALID(1_001_001_005, "CRON 表达式不正确");

    private final Integer code;

    private final String message;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
