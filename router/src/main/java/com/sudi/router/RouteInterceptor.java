package com.sudi.router;

import android.content.Context;

/**
 * Created by sudi on 2017/11/20.
 * Email：sudi@yiche.com
 */

public interface RouteInterceptor {
    boolean intercept(Context context, RouteRequest routeRequest);
}
