package com.bulade.donor.application.controller.admin;

import com.bulade.donor.system.model.User;
import com.bulade.donor.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "测试")
@RestController
@RequestMapping("/api/admin")
public class ApplicationController {

    @Resource
    private UserService userService;

    @Operation(summary = "列表")
    @GetMapping("/hello")
    String hello(String name) {
        return "Hello World!" + name;
    }


    @Operation(summary = "用户")
    @GetMapping("/user")
    User user(Long id) {
        return userService.selectById(id);
    }

}
