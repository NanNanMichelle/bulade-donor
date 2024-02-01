package com.bulade.donor.framework.biz.operatelog.core.service;

/**
 * 操作日志 Framework Service
 */
public interface OperateLogFrameworkService {

    /**
     * 记录操作日志
     *
     * @param operateLog 操作日志请求
     */
    void createOperateLog(OperateLog operateLog);

}
