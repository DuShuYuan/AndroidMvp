package com.dsy.mvp.ui.activity

import com.blankj.utilcode.util.AppUtils
import com.dsy.mvp.R
import com.dsy.mvp.base.BaseActivity
import com.dsy.mvp.presenter.MainContract
import com.dsy.mvp.presenter.MainPresenter
import com.dsy.mvp.utils.DialogHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.listeners.onClick

class MainActivity : BaseActivity<MainPresenter>(R.layout.activity_main), MainContract.View {
    private var exitTime: Long = 0
    private var isExit = false

    override fun initView() {
        window.setBackgroundDrawable(null)
        setToolbar(getString(R.string.app_name))
        showEmptyView()

        btn.onClick {
            //弹窗
            DialogHelper(mActivity){
                //设置弹窗属性
                title = "title"
                content = "这是弹窗内容"
                onConfirmClick = {
                    toast("toast")
                }
            }.show()
        }
    }

    override fun initEvent() {}
    override fun initData() {
        mPresenter.reqData()
    }

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

    }
}