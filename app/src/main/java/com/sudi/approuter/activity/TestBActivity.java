package com.sudi.approuter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.sudi.approuter.R;
import com.sudi.approuter.utils.ActivityPaths;
import com.sudi.approuter.utils.Param;
import com.sudi.route.annotation.ActivityRouter;
import com.sudi.route.annotation.IntentParam;
import com.sudi.router.Router;

/**
 * Created by sudi on 2017/12/1.
 * Email：sudi@yiche.com
 */

@ActivityRouter(path = ActivityPaths.B_ACTIVITY)
public class TestBActivity extends Activity {

    @IntentParam(Param.age)
    public int age;
    @IntentParam(Param.name)
    public String name;
    @IntentParam(Param.sex)
    public String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Router.injectParams(this);

        setContentView(R.layout.activity_show_name);
        TextView tv = (TextView) findViewById(R.id.textView);

        tv.setText("name[" + name + "]\nage[" + age + "]\nsex[" + sex + "]\n");
    }
}
