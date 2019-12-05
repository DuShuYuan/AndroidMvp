package com.dsy.mvp.presenter;


import com.dsy.mvp.base.impl.IView;

public interface MainView extends IView {

    void showUpdateDialog(String versionContent);

    void startDownloadService();
}
