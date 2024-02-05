package com.bulade.donor.system.convert;

import com.bulade.donor.framework.biz.operatelog.bo.OperateLogCreateBO;
import com.bulade.donor.system.model.OperateLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperateLogConvert {

    com.bulade.donor.system.convert.OperateLogConvert INSTANCE =
        Mappers.getMapper(com.bulade.donor.system.convert.OperateLogConvert.class);
    OperateLog convertBoToModel(OperateLogCreateBO createReqBO);
}
