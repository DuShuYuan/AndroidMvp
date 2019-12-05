package com.dsy.mvp.base;


import androidx.annotation.NonNull;

import com.dsy.mvp.base.impl.IPresenter;
import com.dsy.mvp.base.impl.IView;
import com.lzy.okgo.OkGo;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<T extends IView> implements IPresenter {
    protected T mView;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(@NonNull IView iView) {
        mView = (T) iView;
    }

    @Override
    public void detachView() {
        OkGo.getInstance().cancelTag(this);
        onUnSubscribe();
    }

    //RxJava取消注册
    public void onUnSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    public void addSubscription(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

}
