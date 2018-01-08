package com.sudi.router.matcher;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.sudi.route.annotation.model.RouteInfo;
import com.sudi.router.RouteRequest;

/**
 * Absolutely matcher.
 */
public class PathMatcher extends AbsExplicitMatcher {

    public PathMatcher(int priority) {
        super(priority);
    }

    @Override
    public boolean match(Context context, Uri uri, @Nullable RouteInfo route, RouteRequest routeRequest) {
        return !isEmpty(route.mClassPath) && uri.toString().equals(route.mClassPath);
    }

}
