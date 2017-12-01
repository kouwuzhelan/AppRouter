package com.sudi.router;

import android.net.Uri;

/**
 * Created by sudi on 2017/11/20.
 * Email：sudi@yiche.com
 */
public interface RouteCallback {
    /**
     * Callback
     *
     * @param state   {@link RouteResult}
     * @param uri     Uri
     * @param message notice msg
     */
    void callback(RouteResult state, Uri uri, String message);

}
