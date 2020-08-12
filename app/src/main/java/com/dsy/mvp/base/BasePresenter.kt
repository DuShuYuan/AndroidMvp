package com.dsy.mvp.base

import androidx.lifecycle.*
import com.dsy.mvp.base.impl.IPresenter
import com.dsy.mvp.base.impl.IView
import com.dsy.mvp.utils.MLog
import com.lzy.okgo.OkGo
import com.rxlife.coroutine.RxLifeScope
import java.lang.ref.SoftReference


@Suppress("UNCHECKED_CAST")
abstract class BasePresenter<T : IView> : IPresenter, LifecycleObserver {
    protected val TAG = this.javaClass.simpleName
    protected val mView: T?
        get() {
            return mReferenceView?.get() as T
        }
    //弱引用view 防止内存泄露
    private var mReferenceView: SoftReference<IView>? = null

    //协程生命周期自动管理
    protected val rxLifeScope: RxLifeScope
        get() {
            return (mView as LifecycleOwner).rxLifeScope
        }

    override fun attachView(iView: IView) {
        mReferenceView = SoftReference(iView)
        if (mView is LifecycleOwner) {
            (mView as LifecycleOwner).lifecycle.addObserver(this)
        }
    }

    override fun detachView() {
        OkGo.getInstance().cancelTag(this)
        mReferenceView?.clear()
        mReferenceView = null
    }
/*

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart(owner: LifecycleOwner) {
        MLog.d("presenter", "$TAG.onStart()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume(owner: LifecycleOwner) {
        MLog.d("presenter", "$TAG.onResume()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop(owner: LifecycleOwner) {
        MLog.d("presenter", "$TAG.onStop()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause(owner: LifecycleOwner) {
        MLog.d("presenter", "$TAG.onPause()")
    }
*/

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
//        MLog.d("presenter", "$TAG.onDestroy()")
    }
}