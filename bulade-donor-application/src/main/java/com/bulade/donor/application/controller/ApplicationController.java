package com.bulade.donor.application.controller;

import com.bulade.donor.system.model.User;
import com.bulade.donor.system.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/dict")
public class ApplicationController {

    @Resource
    private UserService userService;

    @GetMapping("/hello")
    String hello(String name) {
        return "Hello World!" + name;
    }


    @GetMapping("/user")
    User user(Long id) {
        return userService.selectById(id);
    }

}
