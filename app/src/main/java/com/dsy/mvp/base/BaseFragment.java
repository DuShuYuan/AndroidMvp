package com.dsy.mvp.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.dsy.mvp.base.impl.IPresenter;
import com.dsy.mvp.base.impl.IView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends IPresenter> extends Fragment implements IView {
    protected View view;
    protected Bundle savedInstanceState;
    protected T mPresenter;
    protected String TAG = this.getClass().getSimpleName();
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        attachView();
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        if (null == view) {
            view = inflater.inflate(createLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, view);
            initView();
            initEvent();
            initData();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    /**
     * @return LayoutId
     */
    public abstract int createLayoutId();

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
        return getActivity();
    }

    @Override
    public void toast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void toast(int id) {
        ToastUtils.showShort(id);
    }


    public ProgressDialog progressDialog;

    @Override
    public void showLoadingDialog() {
        progressDialog = new ProgressDialog(getActivity());
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
    public void onDestroy() {
        super.onDestroy();
        detachView();
        unbinder.unbind();
    }


}