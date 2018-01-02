package com.sudi.approuter;

import android.app.Application;

import com.sudi.router.Configuration;
import com.sudi.router.Router;

/**
 * Created by sudi on 2017/12/1.
 * Email：sudi@yiche.com
 */

public class BaseApplication extends Application {

    public static boolean login = false;
    public static boolean bindPhone = false;

    @Override
    public void onCreate() {
        super.onCreate();
        initRoute();
    }

    private void initRoute() {
        Router.initialize(new Configuration.Builder()
                .setDebuggable(BuildConfig.DEBUG)
                .registerModules("app","module1", "module2")
                .build());
    }
}
