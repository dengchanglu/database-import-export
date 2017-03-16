package com.database.utils;

import java.lang.annotation.*;

/**
 * Created by 邓昌路 on 17-3-1.
 */
@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.FIELD, ElementType.METHOD})//定义注解的作用目标**作用范围字段、枚举的常量/方法
@Documented
public @interface HbaseAnnotation {
    String familyName() ;
}
