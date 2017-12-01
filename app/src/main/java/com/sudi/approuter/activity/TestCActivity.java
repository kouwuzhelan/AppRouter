package com.sudi.approuter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.sudi.approuter.R;
import com.sudi.approuter.utils.ActivityPaths;
import com.sudi.approuter.utils.InterceptorName;
import com.sudi.route.annotation.ActivityRouter;

/**
 * Created by sudi on 2017/12/1.
 * Email：sudi@yiche.com
 */

@ActivityRouter(path = ActivityPaths.C_ACTIVITY, interceptors = {InterceptorName.LOGIN_INTERCEPTOR, InterceptorName.BIND_PHONE_INTERCEPTOR})
public class TestCActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_name);
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(this.getClass().getName());
    }
}
