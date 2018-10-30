package com.example.rr.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可配置类，方法，属性，配置后将不会被混淆
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD
        , ElementType.CONSTRUCTOR, ElementType.FIELD})
public @interface KeepNotProgurd {
}
