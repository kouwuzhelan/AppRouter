package com.sudi.approuter.interceptors;

import android.content.Context;
import android.widget.Toast;

import com.sudi.approuter.BaseApplication;
import com.sudi.commonlibrary.InterceptorName;
import com.sudi.route.annotation.Interceptor;
import com.sudi.router.RouteInterceptor;
import com.sudi.router.RouteRequest;

/**
 * Created by sudi on 2017/12/1.
 * Email：sudi@yiche.com
 */
@Interceptor(InterceptorName.BIND_PHONE_INTERCEPTOR)
public class BindPhoneInterceptor implements RouteInterceptor {
    @Override
    public boolean intercept(Context context, RouteRequest routeRequest) {
        if (!BaseApplication.bindPhone) {
            Toast.makeText(context, "绑定手机", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
