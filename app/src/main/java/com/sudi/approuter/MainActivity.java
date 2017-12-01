package com.sudi.approuter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sudi.approuter.utils.ActivityPaths;
import com.sudi.approuter.utils.Param;
import com.sudi.router.IRouter;
import com.sudi.router.Router;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.goA)
    TextView goA;
    @BindView(R.id.goB)
    TextView goB;
    @BindView(R.id.goC)
    TextView goC;
    @BindView(R.id.goD)
    TextView goD;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.bind)
    TextView bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        changeLoginState();
        changeBindState();
    }

    @OnClick(R.id.login)
    void onLoginClick() {
        BaseApplication.login = !BaseApplication.login;
        changeLoginState();
    }

    @OnClick(R.id.bind)
    void onBindClick() {
        BaseApplication.bindPhone = !BaseApplication.bindPhone;
        changeBindState();
    }

    void changeLoginState() {
        if (BaseApplication.login) {
            login.setText("已登录");
        } else {
            login.setText("未登录");
        }
    }

    void changeBindState() {
        if (BaseApplication.bindPhone) {
            bind.setText("已绑定");
        } else {
            bind.setText("未绑定");
        }
    }

    @OnClick({R.id.goA, R.id.goB, R.id.goC, R.id.goD})
    void onGoClick(View v) {
        IRouter build = null;
        switch (v.getId()) {
            case R.id.goA:
                build = Router.build(ActivityPaths.A_ACTIVITY);
                build.with(Param.age, 28).
                        with(Param.name, "sudi").
                        with(Param.sex, "male");
                break;
            case R.id.goB:
                build = Router.build(ActivityPaths.B_ACTIVITY);
                break;
            case R.id.goC:
                build = Router.build(ActivityPaths.C_ACTIVITY);
                break;
            case R.id.goD:
                build = Router.build(ActivityPaths.D_ACTIVITY);
                break;
        }
        if (build != null) {
            build.go(this);
        }
    }
}
