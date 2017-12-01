package com.sudi.route.compiler.utils;

/**
 * Created by sudi on 2017/11/17.
 * Email：sudi@yiche.com
 */

public class Constans {
    public static final String OPTION_MODULE_NAME = "moduleName";
    public static final String CLASS_JAVA_DOC = "Generated by ActivityRouter. Do not edit it!\n";

    public static final String DOT = ".";
    public static final String PACKAGE_NAME = "com.sudi.router";
    public static final String ROUTE_TABLE = "RouteTable";
    public static final String ROUTE_TABLE_FULL_NAME = PACKAGE_NAME + DOT + ROUTE_TABLE;
    public static final String ACTIVITY_FULL_NAME = "android.app.Activity";
    public static final String FRAGMENT_FULL_NAME = "android.app.Fragment";
    public static final String FRAGMENT_V4_FULL_NAME = "android.support.v4.app.Fragment";

    public static final String ROUTE_ANNOTATION_TYPE = "com.sudi.route.annotation.ActivityRouter";
    public static final String INTERCEPTOR_ANNOTATION_TYPE = "com.sudi.route.annotation.Interceptor";
    public static final String PARAM_ANNOTATION_TYPE = "com.sudi.route.annotation.IntentParam";

    public static final String HANDLE = "handle";
    public static final String INTERCEPTOR_INTERFACE = PACKAGE_NAME + DOT + "RouteInterceptor";

    public static final String INTERCEPTOR_TABLE = "InterceptorTable";

    public static final String TABLE_INTERCEPTORS = "TargetInterceptors";

    public static final String METHOD_INJECT = "inject";
    // XXXActivity$$ActivityRouter$$Params
    public static final String INNER_CLASS_NAME = "$$ActivityRouter$$ParamInjector";
}