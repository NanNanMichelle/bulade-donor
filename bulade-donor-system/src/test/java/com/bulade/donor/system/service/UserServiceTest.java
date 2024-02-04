package com.bulade.donor.system.service;

import com.bulade.donor.system.TestApplication;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestApplication.class)
@Transactional
class UserServiceTest {

    @Resource
    private UsersService userService;

    @Test
    void testSelectById() {
        var user = userService.selectById(1L);
        assertThat(user).isNull();
    }

}
