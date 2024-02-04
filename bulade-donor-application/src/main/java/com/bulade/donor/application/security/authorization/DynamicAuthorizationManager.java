package com.bulade.donor.application.security.authorization;

import com.bulade.donor.authorization.enums.PermissionType;
import com.bulade.donor.authorization.model.Permission;
import com.bulade.donor.authorization.service.PermissionsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Component
public class DynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private static final AuthorizationDecision PASS = new AuthorizationDecision(true);

    private static final AuthorizationDecision DENY = new AuthorizationDecision(false);

    @Resource
    private PermissionsService permissionsService;

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext object) {
        var request = object.getRequest();
        var requestURI = request.getRequestURI();
        var authentication = supplier.get();

        // 无需登录即可访问的接口
        var noSignInPermissions = permissionsService.listByType(PermissionType.NO_SIGN_IN);
        var noSignInPermission = determinePermission(noSignInPermissions, request);
        if (noSignInPermission != null) {
            log.info("当前为无需登录即可访问的接口permissionId[{}] requestURI[{}]", noSignInPermission.getId(), requestURI);
            return PASS;
        }

        // 后续的权限验证逻辑，需要用户登录
        if (authentication instanceof AnonymousAuthenticationToken) {
            log.info("用户未登录 requestURI[{}]", requestURI);
            return DENY;
        }

        // 查找当前的url对应的permission
        var permissions = permissionsService.listByType(PermissionType.AUTHORIZATION);
        var permission = determinePermission(permissions, request);
        if (permission == null) {
            log.info("没有匹配到定义的权限，直接放行 requestURI[{}]", requestURI);
            return PASS;
        }

        // 检测当前用户是否具有这个permission
        var permissionId = permission.getId();
        var authorities = authentication.getAuthorities();
        if (authorities.contains(new SimpleGrantedAuthority(permissionId.toString()))) {
            return PASS;
        }
        log.info("当前用户不存在权限permissionId[{}] requestURI[{}]", permissionId, requestURI);
        return DENY;
    }

    private Permission determinePermission(List<Permission> permissions, HttpServletRequest request) {
        var introspector = applicationContext.getBean(HandlerMappingIntrospector.class);
        for (Permission permission : permissions) {
            var mvcRequestMatcher = new MvcRequestMatcher(introspector, permission.getController());
            var matchResult = mvcRequestMatcher.matcher(request);
            if (matchResult.isMatch()) {
                return permission;
            }
        }
        return null;
    }
}
