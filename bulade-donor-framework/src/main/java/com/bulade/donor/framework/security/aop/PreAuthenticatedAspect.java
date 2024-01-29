package com.bulade.donor.framework.security.aop;

import com.bulade.donor.framework.security.annotations.PreAuthenticated;
import com.bulade.donor.common.exception.UnAuthorizedException;
import com.bulade.donor.framework.security.utils.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Slf4j
public class PreAuthenticatedAspect {

    @Around("@annotation(preAuthenticated)")
    public Object around(ProceedingJoinPoint joinPoint, PreAuthenticated preAuthenticated) throws Throwable {
        if (SecurityFrameworkUtils.getLoginUser() == null) {
            throw new UnAuthorizedException();
        }
        return joinPoint.proceed();
    }

}

