package com.sudi.module2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sudi.commonlibrary.ActivityPaths;
import com.sudi.commonlibrary.Param;
import com.sudi.commonlibrary.TestActivitySchemas;
import com.sudi.module2.R;
import com.sudi.route.annotation.ActivityRouter;
import com.sudi.route.annotation.IntentParam;
import com.sudi.router.Router;

/**
 * Created by sudi on 2018/1/4.
 * Email：sudi@yiche.com
 */
@ActivityRouter(path = ActivityPaths.A_FRAGMENT)
public class AFragment extends Fragment {
    @IntentParam(Param.age)
    public int age;
    @IntentParam(Param.name)
    public String name;
    @IntentParam(Param.sex)
    public String sex;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Router.injectParams(this);

        TextView tv = (TextView) getView().findViewById(R.id.textView);
        tv.setText("AFragment\nname[" + name + "]\nage[" + age + "]\nsex[" + sex + "]\n");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_show_name, container, false);
    }
}
