package com.bulade.donor.system.bo;

import com.bulade.donor.system.convert.UserConvert;
import com.bulade.donor.system.dto.UserDTO;
import com.bulade.donor.system.model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDtoConvertTest {

    @Test
    void testUserConvert() {
        // 创建 User 对象
        var userModel = new User();
        userModel.setId(1L);
        userModel.setUserName("bulade");
        userModel.setPassword("donor");

        UserDTO userDto = UserConvert.INSTANCE.convertModelToDto(userModel);
        assertThat(userDto.getId()).isEqualTo(userModel.getId());
        assertThat(userDto.getUserName()).isEqualTo("bulade");
        assertThat(userDto.getPassword()).isEqualTo("donor");


        var userBo = new UserBO();
        userBo.setId(1L);
        userBo.setUserName("bulade-bo");
        userBo.setPassword("donor-bo");

        var model = UserConvert.INSTANCE.convertBoToModel(userBo);
        assertThat(model.getUserName()).isEqualTo("bulade-bo");
        assertThat(model.getPassword()).isEqualTo("donor-bo");

    }

}