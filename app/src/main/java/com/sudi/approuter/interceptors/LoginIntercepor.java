package com.sudi.approuter.interceptors;

import android.content.Context;
import android.widget.Toast;

import com.sudi.approuter.BaseApplication;
import com.sudi.approuter.utils.InterceptorName;
import com.sudi.route.annotation.Interceptor;
import com.sudi.router.RouteInterceptor;
import com.sudi.router.RouteRequest;

/**
 * Created by sudi on 2017/12/1.
 * Email：sudi@yiche.com
 */
@Interceptor(name = InterceptorName.LOGIN_INTERCEPTOR)
public class LoginIntercepor implements RouteInterceptor {

    @Override
    public boolean intercept(Context context, RouteRequest routeRequest) {
        if (!BaseApplication.login) {
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
