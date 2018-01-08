package com.sudi.module2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.sudi.commonlibrary.ActivityPaths;
import com.sudi.commonlibrary.Param;
import com.sudi.route.annotation.ActivityRouter;
import com.sudi.route.annotation.IntentParam;
import com.sudi.router.Router;

/**
 * Created by sudi on 2017/12/1.
 * Email：sudi@yiche.com
 */

@ActivityRouter(path = ActivityPaths.D_ACTIVITY)
public class TestDActivity extends FragmentActivity {

    @IntentParam(Param.age)
    public int age;
    @IntentParam(Param.name)
    public String name;
    @IntentParam(Param.sex)
    public String sex;

    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fragment);

        Router.injectParams(this);

        showFragment();
    }

    private void showFragment() {
        if (mFragment == null) {
            mFragment = (Fragment) Router.build(ActivityPaths.A_FRAGMENT).
                    with(Param.age, age).
                    with(Param.name, name).
                    with(Param.sex, sex).getFragment(this);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.view_container, mFragment).commitAllowingStateLoss();
    }
}
