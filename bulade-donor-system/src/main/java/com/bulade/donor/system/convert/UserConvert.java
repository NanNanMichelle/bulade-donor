package com.bulade.donor.system.convert;

import com.bulade.donor.system.bo.UserBO;
import com.bulade.donor.system.dto.UserDTO;
import com.bulade.donor.system.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper //声明它是一个 MapStruct Mapper 映射器。
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class); //获得 MapStruct 帮我们自动生成的 UserConvert 实现类的对象。

    UserDTO convertModelToDto(User user);

    User convertBoToModel(UserBO userBO);
}
