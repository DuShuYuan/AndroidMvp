package com.dsy.mvp.base

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import com.dsy.mvp.base.impl.IPresenter
import com.dsy.mvp.base.impl.IView
import com.dylanc.loadinghelper.LoadingHelper
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseFragment<T : IPresenter>(private val layoutId: Int) : Fragment(), IView {
    private var rootView: View? = null
    protected lateinit var mPresenter: T
    protected var TAG = this.javaClass.simpleName
    protected var isViewBind = false
    private var isFirstVisible = true //第一次展示
    protected lateinit var mLoadingHelper:LoadingHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = initPresenter()
        mPresenter.attachView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == rootView) {
            val view = inflater.inflate(layoutId, container, false)
            rootView = initLoadingHelper(view)
            mLoadingHelper.setOnReloadListener { onReload() }
            isViewBind = false
        }
//        val parent = rootView?.parent as ViewGroup?
//        parent?.removeView(rootView)
        return mLoadingHelper.decorView
    }

    protected open fun initLoadingHelper(rootView: View): View {
        mLoadingHelper = LoadingHelper(rootView)
        return mLoadingHelper.decorView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isViewBind) {
            initView()
            initEvent()
            isViewBind = true
        }
    }

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

    override fun showLoadingView() {
        mLoadingHelper.showLoadingView()
    }

    override fun showContentView() {
        mLoadingHelper.showContentView()
    }

    override fun showErrorView() {
        mLoadingHelper.showErrorView()
    }

    override fun showEmptyView() {
        mLoadingHelper.showEmptyView()
    }

    override fun showCustomView(viewType: Any) {
        mLoadingHelper.showView(viewType)
    }

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

    protected open fun onReload() {}

    override val mActivity: Activity
        get() = activity!!

    override fun toast(msg: CharSequence) {
        ToastUtils.showShort(msg)
    }

    override fun toast(@StringRes id: Int) {
        ToastUtils.showShort(id)
    }

    private var progressDialog: ProgressDialog? = null
    override fun showLoadingDialog() {
        progressDialog = ProgressDialog(activity)
        progressDialog?.setMessage("加载中")
        progressDialog?.show()
    }

    override fun hideLoadingDialog() {
        progressDialog?.dismiss()
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }
}