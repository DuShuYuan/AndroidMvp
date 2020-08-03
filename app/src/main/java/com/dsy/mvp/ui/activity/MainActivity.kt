package com.dsy.mvp.ui.activity

import com.blankj.utilcode.util.AppUtils
import com.dsy.mvp.R
import com.dsy.mvp.base.BaseActivity
import com.dsy.mvp.presenter.MainContract
import com.dsy.mvp.presenter.MainPresenter

class MainActivity : BaseActivity<MainPresenter>(R.layout.activity_main), MainContract.View {
    private var exitTime: Long = 0
    private var isExit = false

    override fun initView() {
        setToolbar(getString(R.string.app_name))
        showEmptyView()
    }

    override fun initEvent() {}
    override fun initData() {}

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            toast("再按一次退出程序")
            exitTime = System.currentTimeMillis()
        } else {
            isExit = true
            super.onBackPressed()
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        if (isExit) {
            AppUtils.exitApp()
        }
    }

    override fun onSuccess() {
        TODO("Not yet implemented")
    }
}