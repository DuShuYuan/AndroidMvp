package com.dsy.mvp.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;
import com.dsy.mvp.base.impl.IPresenter;
import com.dsy.mvp.base.impl.IView;
import com.dsy.mvp.component.ActivityManager;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity implements IView {

    protected final String TAG = this.getClass().getSimpleName();
    public ProgressDialog progressDialog;
    protected T mPresenter;
    protected Bundle savedInstanceState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        beforeCreate();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        ActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        mPresenter = createPresenter();
        attachView();
        initView();
        initEvent();
        initData();
    }

    /**
     * 布局载入之前
     */
    protected void beforeCreate() {
    }

    /**
     * 布局载入
     */
    protected abstract int getLayoutId();

    /**
     * P层绑定   若无则返回null;
     */
    protected abstract T createPresenter();

    /**
     * 控件绑定
     */
    protected abstract void initView();

    /**
     * 事件触发绑定
     */
    protected abstract void initEvent();

    /**
     * 数据初始化
     */
    protected abstract void initData();

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * P层绑定V层
     */
    private void attachView() {
        if (null != mPresenter) {
            mPresenter.attachView(this);
        }
    }

    /**
     * P层解绑V层
     */
    private void detachView() {
        if (null != mPresenter) {
            mPresenter.detachView();
        }
    }

    @Override
    public Activity getMActivity() {
        return this;
    }

    @Override
    public void toast(int id) {
        ToastUtils.showShort(id);
    }

    @Override
    public void toast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void showLoadingDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中");
        progressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachView();
        ActivityManager.getInstance().removeActivity(this);
    }

}