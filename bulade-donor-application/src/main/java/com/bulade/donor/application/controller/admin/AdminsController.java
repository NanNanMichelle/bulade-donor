package com.bulade.donor.application.controller.admin;

import com.bulade.donor.application.payload.request.AdminSignInReq;
import com.bulade.donor.application.payload.response.AuthLoginResp;
import com.bulade.donor.application.service.AdminAuthenticationService;
import com.bulade.donor.common.enums.UserType;
import com.bulade.donor.framework.biz.operatelog.core.annotations.OperateLog;
import com.bulade.donor.system.model.User;
import com.bulade.donor.system.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理员-平台")
@RestController("adminController")
@RequestMapping("/api/admin")
@Validated
public class AdminsController {

    @Resource
    private AdminAuthenticationService adminAuthenticationService;

    @Resource
    private UsersService adminsService;

    @Resource
    private UsersService userService;

    @PostMapping("/sign-in")
    public AuthLoginResp signIn(@Valid @RequestBody AdminSignInReq adminSignInReq) {
        var user = adminAuthenticationService.signIn(adminSignInReq.getUsername(), adminSignInReq.getPassword());
        return AuthLoginResp.of(user, UserType.ADMIN.getCode());
    }

    @Operation(summary = "列表")
    @GetMapping("/hello")
    Object hello(String name) {
        return "Hello World! " + name + "\n 是个Get请求，默认不记录操作日志";
    }

    @Operation(summary = "列表")
    @GetMapping("/hello/get")
    @OperateLog(module = "操作模块")
    Object helloa(String name) {
        return "Hello World! " + name + "\n 是个Get请求，有 @OperateLog 记录操作日志";
    }

    @Operation(summary = "列表")
    @PostMapping("/add")
    Object add() {
        return "是个post请求，默认记录操作日志";
    }

    @Operation(summary = "用户")
    @GetMapping("/user")
    User user(Long id) {
        return userService.selectById(id);
    }

}
