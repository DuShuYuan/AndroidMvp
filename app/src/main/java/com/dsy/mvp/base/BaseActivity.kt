package com.dsy.mvp.base

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.dsy.mvp.base.impl.IPresenter
import com.dsy.mvp.base.impl.IView
import com.dsy.mvp.ui.adapter.NavIconType
import com.dsy.mvp.ui.adapter.ToolbarAdapter
import com.dsy.mvp.widget.status.LoadStatus
import com.dsy.mvp.widget.status.StatusHolder
import com.dylanc.loadinghelper.LoadingHelper
import com.dylanc.loadinghelper.ViewType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseActivity<T : IPresenter>(private val layoutId: Int) : AppCompatActivity(), IView {
    protected val TAG = this.javaClass.simpleName
    var progressDialog: ProgressDialog? = null
    protected lateinit var mPresenter: T
    protected lateinit var mLoadingHelper: LoadingHelper
    private lateinit var mStatusHolder: StatusHolder.Holder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeCreate()
        if (layoutId != 0) {
            setContentView(layoutId)
            initStatusHolder()
            mLoadingHelper = LoadingHelper(this)
            mStatusHolder.withRetry { onReload() }
        }
        mPresenter = initPresenter()
        mPresenter.attachView(this)
        initView()
        initEvent()
        initData()
    }

    private fun initStatusHolder() {
        mStatusHolder = if (getStatusWrapView() != null) {
            StatusHolder.getDefault().wrap(getStatusWrapView())
        } else {
            StatusHolder.getDefault().wrap(this)
        }
    }

    /**
     * 状态布局包裹的view
     */
    protected open fun getStatusWrapView(): View? {
        return null
    }
    override fun showLoadingView() {
        mStatusHolder.showLoading()
    }

    override fun showContentView() {
        mStatusHolder.showLoadSuccess()
    }

    override fun showErrorView() {
        mStatusHolder.showLoadFailed()
    }

    override fun showEmptyView() {
        mStatusHolder.showEmpty()
    }

    override fun showCustomView(status: LoadStatus) {
        mStatusHolder.showLoadingStatus(status)
    }


    protected fun setToolbar(title: String, type: NavIconType = NavIconType.NONE) {
        mLoadingHelper.apply {
            register(ViewType.TITLE, ToolbarAdapter(title, type))
            setDecorHeader(ViewType.TITLE)
        }
    }

    protected open fun onReload() {}

    @Suppress("UNCHECKED_CAST")
    protected fun initPresenter(): T {
        val type: Type? = javaClass.genericSuperclass
        val modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<*>
        } else {
            //如果没有指定泛型参数，则默认使用EmptyPresenter
            SimplePresenter::class.java
        }
        return modelClass.newInstance() as T
    }


    /**
     * 布局载入之前
     */
    protected open fun beforeCreate() {}

    /**
     * 控件绑定
     */
    protected abstract fun initView()

    /**
     * 事件触发绑定
     */
    protected abstract fun initEvent()

    /**
     * 数据初始化
     */
    protected abstract fun initData()

    override val mActivity: Activity
        get() = this

    override fun toast(@StringRes id: Int) {
        ToastUtils.showShort(id)
    }

    override fun toast(msg: CharSequence) {
        ToastUtils.showShort(msg)
    }

    override fun showLoadingDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage("加载中")
        progressDialog?.show()
    }

    override fun hideLoadingDialog() {
        progressDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}