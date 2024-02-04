package com.bulade.donor.system.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobLog {

    /**
     * 日志编号
     */
    private Long id;

    /**
     * 任务编号
     */
    private Long jobId;

    /**
     * 处理器的名字
     */
    private String handlerName;
    /**
     * 处理器的参数
     */
    private String handlerParam;

    /**
     * 第几次执行
     *
     * 用于区分是不是重试执行。如果是重试执行，则 index 大于 1
     */
    private Integer executeIndex;

    /**
     * 开始执行时间
     */
    private LocalDateTime beginTime;

    /**
     * 结束执行时间
     */
    private LocalDateTime endTime;

    /**
     * 执行时长，单位：毫秒
     */
    private Integer duration;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 结果数据
     */
    private String result;
}
