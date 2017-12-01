package com.sudi.router;

import android.net.Uri;

import com.sudi.router.matcher.AbsExplicitMatcher;
import com.sudi.router.matcher.AbsImplicitMatcher;
import com.sudi.router.matcher.AbsMatcher;
import com.sudi.router.util.RLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sudi on 2017/11/20.
 * Email：sudi@yiche.com
 */

public class Router {
    /**
     * You can get the raw uri in target page by call <code>intent.getStringExtra(ActivityRouter.RAW_URI)</code>.
     */
    public static final String RAW_URI = "raw_uri";
    private static List<RouteInterceptor> sGlobalInterceptors = new ArrayList<>();


    public static void initialize(Configuration configuration) {
        RLog.showLog(configuration.debuggable);
        AptHub.registerModules(configuration.modules);
    }

    public static IRouter build(String path) {
        return build(path == null ? null : Uri.parse(path));
    }

    public static IRouter build(Uri uri) {
        return _Router.getInstance().build(uri);
    }

    /**
     * Use {@link #handleRouteTable(RouteTable)} instead.
     * <p>
     * This method will be <strong>removed</strong> in a future release.
     */
    @Deprecated
    public static void addRouteTable(RouteTable routeTable) {
        handleRouteTable(routeTable);
    }

    /**
     * Custom route table.
     */
    public static void handleRouteTable(RouteTable routeTable) {
        _Router.getInstance().handleRouteTable(routeTable);
    }

    /**
     * Custom interceptor table.
     */
    public static void handleInterceptorTable(InterceptorTable interceptorTable) {
        _Router.getInstance().handleInterceptorTable(interceptorTable);
    }

    /**
     * Custom targets' interceptors.
     */
    public static void handleTargetInterceptors(TargetInterceptors targetInterceptors) {
        _Router.getInstance().handleTargetInterceptors(targetInterceptors);
    }

    /**
     * Auto inject params from bundle.
     *
     * @param obj Instance of Activity or Fragment.
     */
    public static void injectParams(Object obj) {
        _Router.getInstance().injectParams(obj);
    }

    /**
     * Global interceptor.
     */
    public static void addGlobalInterceptor(RouteInterceptor routeInterceptor) {
        sGlobalInterceptors.add(routeInterceptor);
    }

    public static List<RouteInterceptor> getGlobalInterceptors() {
        return sGlobalInterceptors;
    }

    /**
     * Register your own matcher.
     *
     * @see AbsExplicitMatcher
     * @see AbsImplicitMatcher
     */
    public static void registerMatcher(AbsMatcher matcher) {
        MatcherRegistry.register(matcher);
    }

    public static void clearMatcher() {
        MatcherRegistry.clear();
    }
}
