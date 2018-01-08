package com.sudi.route.annotation.model;

/**
 * Created by sudi on 2018/1/4.
 * Email：sudi@yiche.com
 */

public class RouteInfo {
    /**
     * 类完整路径
     */
    public String mClassPath;
    /**
     * schema地址
     */
    public String mSchema;
    /**
     * 别名
     */
    public String mAlias;

    public Class<?> mTarget;

    public RouteInfo() {
    }

    public RouteInfo(Class<?> cls, String classPath, String schema, String alias) {
        this.mClassPath = classPath;
        this.mTarget = cls;
        this.mSchema = schema;
        this.mAlias = alias;
    }

    public static RouteInfo build(Class<?> cls, String classPath, String schema, String alias) {
        return new RouteInfo(cls, classPath, schema, alias);
    }

    public String toString() {
        return "mClassPath[" + mClassPath
                + "],\nmSchema[" + mSchema
                + "],\nmAlias[" + mAlias
                + "],\nmTarget[" + mTarget.getSimpleName() + "]";
    }
}
