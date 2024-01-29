package com.bulade.donor.framework.security.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Slf4j
@Component
public class DynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private static final AuthorizationDecision PASS = new AuthorizationDecision(true);

    private static final AuthorizationDecision DENY = new AuthorizationDecision(false);

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        var request = object.getRequest();
        var requestURI = request.getRequestURI();
        //临时
        if("/api/admin/user".equals(requestURI)){
            return DENY;
        }
        return PASS;
    }
}
