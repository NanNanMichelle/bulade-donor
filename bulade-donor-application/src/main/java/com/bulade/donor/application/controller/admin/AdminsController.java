package com.bulade.donor.application.controller.admin;

import com.bulade.donor.application.payload.request.AdminSignInReq;
import com.bulade.donor.application.payload.response.AuthLoginResp;
import com.bulade.donor.application.service.AdminAuthenticationService;
import com.bulade.donor.framework.security.utils.WebFrameworkUtils;
import com.bulade.donor.system.enums.UserType;
import com.bulade.donor.system.model.User;
import com.bulade.donor.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
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
    private UserService adminsService;

    @Resource
    private UserService userService;

    @PostMapping("/login")
    @Operation(summary = "登录")
    @PermitAll
    public AuthLoginResp signIn(@Valid @RequestBody AdminSignInReq adminSignInReq) {
        var user = adminAuthenticationService.signIn(adminSignInReq.getUsername(), adminSignInReq.getPassword());
        return AuthLoginResp.of(user, UserType.ADMIN.getCode());
    }

    @Operation(summary = "列表")
    @GetMapping("/hello")
//    @PreAuthorize("hasAuthority('test')")
    Object hello(String name) {
        return "Hello World!" + name;
    }

    @Operation(summary = "用户")
    @GetMapping("/user")
    @PermitAll
    User user(Long id) {
        return userService.selectById(id);
    }

}
