package com.dsy.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.dsy.mvp.R;
import com.dsy.mvp.base.BaseActivity;
import com.dsy.mvp.component.PermissionsRequest;
import com.dsy.mvp.presenter.SplashView;
import com.dsy.mvp.presenter.SplashPresenter;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashView{

    //TODO 需要申请的权限
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_PHONE_STATE
    };
    private PermissionsRequest permissionsRequest;
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected void initView() {

        permissionsRequest = new PermissionsRequest(this) {
            @Override
            protected void onPermissionsGranted() {
                toMain();
            }

            @Override
            protected void onPermissionsDenied() {
                finish();
            }
        };
        permissionsRequest.requestPermissions(PERMISSIONS);
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void toMain() {
        startActivity(new Intent(getMActivity(),MainActivity.class));
        finish();
    }
}
