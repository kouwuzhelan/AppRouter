package com.sudi.router.matcher;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.sudi.route.annotation.model.RouteInfo;
import com.sudi.router.RouteRequest;
import com.sudi.router.util.RLog;

/**
 * Created by sudi on 2018/1/5.
 * Email：sudi@yiche.com
 */

public class RouteInfoMatcher extends AbsExplicitMatcher {
    public RouteInfoMatcher(int priority) {
        super(priority);
    }

    @Override
    public boolean match(Context context, Uri uri, @Nullable RouteInfo route, RouteRequest routeRequest) {
        RLog.e("RouteInfoMatcher match() \nuri[" + uri + "] ,\nRouteInfo ----" + route.toString());
        boolean match = false;
        if (routeRequest.isRouteToActivity()) {
            //activity 先判断路径，后判断schema
            match = matchPath(uri, route);
            if (!match) {
                match = matchSchema(uri, route, routeRequest);
            }
        } else {
            //fragment直接用别名判断
            match = matchAlias(uri, route);
        }

        return match;
    }

    private boolean matchAlias(Uri uri, RouteInfo route) {
        return !isEmpty(route.mAlias) && uri.toString().equals(route.mAlias);
    }


    /**
     * 匹配路径
     *
     * @param uri
     * @param route
     * @return
     */
    private boolean matchPath(Uri uri, RouteInfo route) {
        return !isEmpty(route.mClassPath) && uri.toString().equals(route.mClassPath);
    }

    /**
     * 匹配schema
     *
     * @param uri
     * @param route
     * @return
     */
    private boolean matchSchema(Uri uri, RouteInfo route, RouteRequest routeRequest) {
        if (isEmpty(route.mSchema)) {
            return false;
        }
        Uri routeUri = Uri.parse(route.mSchema);
        if (uri.isAbsolute() && routeUri.isAbsolute()) { // scheme != null
            if (!uri.getScheme().equals(routeUri.getScheme())) {
                // http != https
                return false;
            }
            if (isEmpty(uri.getAuthority()) && isEmpty(routeUri.getAuthority())) {
                // host1 == host2 == empty
                return true;
            }
            // google.com == google.com:443 (include port)
            if (!isEmpty(uri.getAuthority()) && !isEmpty(routeUri.getAuthority())
                    && uri.getAuthority().equals(routeUri.getAuthority())) {
                if (!cutSlash(uri.getPath()).equals(cutSlash(routeUri.getPath()))) {
                    return false;
                }
                // bundle parser
                if (uri.getQuery() != null) {
                    parseParams(uri, routeRequest);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 剔除path头部和尾部的斜杠/
     *
     * @param path 路径
     * @return 无/的路径
     */
    private String cutSlash(String path) {
        if (path.startsWith("/")) {
            return cutSlash(path.substring(1));
        }
        if (path.endsWith("/")) {
            return cutSlash(path.substring(0, path.length() - 1));
        }
        return path;
    }
}
