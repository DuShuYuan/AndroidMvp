package com.dsy.mvp.ui.activity

import android.Manifest
import android.content.Intent
import com.dsy.mvp.base.BaseActivity
import com.dsy.mvp.base.SimpleContract
import com.dsy.mvp.base.SimplePresenter
import com.dsy.mvp.component.PermissionsRequest

class SplashActivity : BaseActivity<SimplePresenter>(0) ,SimpleContract.View{

    //TODO 需要申请的权限
    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private lateinit var permissionsRequest: PermissionsRequest
    override fun initView() {
        permissionsRequest = object : PermissionsRequest(this) {
            override fun onPermissionsGranted() {
                toMain()
            }

            override fun onPermissionsDenied() {
                finish()
            }
        }
        permissionsRequest.requestPermissions(*permissions)
    }

    override fun initEvent() {}
    override fun initData() {}
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsRequest.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun toMain() {
        startActivity(Intent(mActivity, MainActivity::class.java))
        finish()
    }

}