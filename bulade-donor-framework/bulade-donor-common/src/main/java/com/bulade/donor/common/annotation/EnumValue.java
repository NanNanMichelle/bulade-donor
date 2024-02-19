package com.bulade.donor.common.annotation;

import java.lang.annotation.*;

/**
 * 支持普通枚举类字段, 只用在enum类的字段上
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface EnumValue {

}
