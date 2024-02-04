package com.bulade.donor.system.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 定时任务创建/修改 Request VO")
@Data
public class JobBO {

    private Long id;

    private String name;

    private String handlerName;

    private String handlerParam;

    private String cronExpression;

    private Integer retryCount;

    private Integer retryInterval;

    private Integer monitorTimeout;

}
