package com.bulade.donor.common.annotation;

import java.lang.annotation.*;

@Inherited
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveField {

    SensitiveType type() default SensitiveType.NO;

}
