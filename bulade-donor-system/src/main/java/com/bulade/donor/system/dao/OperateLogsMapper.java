package com.bulade.donor.system.dao;

import com.bulade.donor.system.model.OperateLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperateLogsMapper {
    Integer insert(OperateLog operateLog);
}
