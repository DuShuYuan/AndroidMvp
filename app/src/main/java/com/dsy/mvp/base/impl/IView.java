package com.dsy.mvp.base.impl;

import android.app.Activity;

public interface IView {
    Activity getMActivity();

    void toast(String msg);

    void toast(int id);

    void showLoadingDialog();

    void hideLoadingDialog();

}
