package com.bulade.donor.application.payload.convert;

import com.bulade.donor.application.payload.request.JobReq;
import com.bulade.donor.system.bo.JobBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobConvert {

    JobConvert INSTANCE = Mappers.getMapper(JobConvert.class);

    JobBO convertModelToBO(JobReq job);

}
