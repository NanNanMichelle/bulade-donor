package com.bulade.donor.framework.security.annotations;

import java.lang.annotation.*;

/**
 * 声明用户需要登录
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuthenticated {
}

