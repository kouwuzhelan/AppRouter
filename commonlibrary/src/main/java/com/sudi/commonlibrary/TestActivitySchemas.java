package com.sudi.commonlibrary;

/**
 * Created by sudi on 2017/12/4.
 * Email：sudi@yiche.com
 */

public class TestActivitySchemas {

    public final static String SCHEMA = "bitauto.yicheapp://yiche.app/%s";

    public final static String A_ACTIVITY_SCHEMA = String.format(SCHEMA, ActivityPaths.A_ACTIVITY);
    public final static String B_ACTIVITY_SCHEMA = String.format(SCHEMA, ActivityPaths.B_ACTIVITY);
    public final static String C_ACTIVITY_SCHEMA = String.format(SCHEMA, ActivityPaths.C_ACTIVITY);
    public final static String D_ACTIVITY_SCHEMA = String.format(SCHEMA, ActivityPaths.D_ACTIVITY);

    public static String withParam(String url) {
        StringBuilder sb = new StringBuilder(url);
        sb.append("?").
                append(Param.age).append("=").append(29).append("&").
                append(Param.name).append("=").append("schema携带参数").append("&").
                append(Param.sex).append("=").append("男");
        return sb.toString();
    }
}
