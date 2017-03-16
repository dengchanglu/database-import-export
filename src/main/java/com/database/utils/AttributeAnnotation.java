package com.database.utils;

import java.lang.annotation.*;

/**
 * Created by 邓昌路 on 17-3-16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Documented
public @interface AttributeAnnotation {

    String attributeName();
}
