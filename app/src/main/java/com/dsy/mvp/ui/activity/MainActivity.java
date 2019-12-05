package com.dsy.mvp.ui.activity;

import android.os.Process;
import android.view.KeyEvent;

import com.dsy.mvp.R;
import com.dsy.mvp.base.BaseActivity;
import com.dsy.mvp.component.ActivityManager;
import com.dsy.mvp.presenter.MainPresenter;
import com.dsy.mvp.presenter.MainView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    private long exitTime = 0;
    private boolean isExit = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initView() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mPresenter.addSubscription(d);
                    }

                    @Override
                    public void onNext(String s) {


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        mPresenter.addSubscription(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                }));
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showUpdateDialog(String versionContent) {

    }

    @Override
    public void startDownloadService() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitApp();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            toast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            isExit = true;
            ActivityManager.getInstance().finishAll();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isExit) {
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
    }

}
