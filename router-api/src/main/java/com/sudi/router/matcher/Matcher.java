package com.sudi.router.matcher;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.sudi.route.annotation.model.RouteInfo;
import com.sudi.router.RouteRequest;

/**
 * Match rule.
 * <p>
 */
interface Matcher extends Comparable<Matcher> {
    /**
     * Determines if the given uri matches current route.
     *
     * @param context      Context.
     * @param uri          the given uri.
     * @param route        path in route table.
     * @param routeRequest {@link RouteRequest}.
     * @return True if matched, false otherwise.
     */
    boolean match(Context context, Uri uri, @Nullable RouteInfo route, RouteRequest routeRequest);

    /**
     * Called when {@link #match(Context, Uri, RouteInfo, RouteRequest)} returns true.
     *
     * @param context Context.
     * @param uri     The given uri.
     * @param target  Route target. Activity or Fragment.
     * @return An object(intent/fragment) that the matcher generated.
     */
    Object generate(Context context, Uri uri, @Nullable Class<?> target);
}
