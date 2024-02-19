package com.bulade.donor.system.dao;

import com.bulade.donor.system.model.Job;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JobMapper {
    Job selectByHandlerName(String handlerName);

    Integer insert(Job job);

    Integer updateById(Job updateObj);

    Job selectById(Long id);

    Integer deleteById(Long id);
}
