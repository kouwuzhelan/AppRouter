package com.sudi.route.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sudi on 2017/12/1.
 * Email：sudi@yiche.com
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ActivityRouter {
    String path();

    String schema() default "bitauto.yicheapp";

    String host() default "yiche.app";

    String[] interceptors() default {};
}
