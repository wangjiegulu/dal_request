package com.wangjiegulu.dal.request.gson.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 08/08/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GsonExclude {
    boolean serialize() default true;

    boolean deserialize() default true;
}
