package com.dsy.mvp.widget.status;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.dsy.mvp.BuildConfig;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/7/6
 *     desc  : 自定义状态管理，用于list条目中
 *     revise: 借鉴大神齐翔开源库
 *             参考链接：https://github.com/luckybilly/Gloading
 * </pre>
 */
public class StatusHolder {

    private static volatile StatusHolder mDefault;
    private Adapter mAdapter;
    private static boolean DEBUG = BuildConfig.DEBUG;

    /**
     * 提供显示当前加载状态的视图
     */
    public interface Adapter {
        /**
         * get view for current status
         *
         * @param holder      Holder
         * @param convertView The old view to reuse, if possible.
         * @param status      current status
         * @return status view to show. Maybe convertView for reuse.
         * @see Holder
         */
        View getView(Holder holder, View convertView, LoadStatus status);
    }

    /**
     * set debug mode or not
     *
     * @param debug true:debug mode, false:not debug mode
     */
    public static void debug(boolean debug) {
        DEBUG = debug;
    }

    /**
     * 构造方法私有话，避免外部通过new进行初始化
     */
    private StatusHolder() {
    }

    /**
     * Create a new StateViewLayout different from the default one
     *
     * @param adapter another adapter different from the default one
     * @return StateViewLayout
     */
    public static StatusHolder from(Adapter adapter) {
        checkAdapter(adapter);
        StatusHolder statusHolder = new StatusHolder();
        statusHolder.mAdapter = adapter;
        return statusHolder;
    }

    /**
     * get default StateViewLayout object for global usage in whole app
     *
     * @return default StateViewLayout object
     */
    public static StatusHolder getDefault() {
        if (mDefault == null) {
            synchronized (StatusHolder.class) {
                if (mDefault == null) {
                    mDefault = new StatusHolder();
                }
            }
        }
        return mDefault;
    }

    /**
     * init the default loading status view creator ({@link Adapter})
     *
     * @param adapter adapter to create all status views
     */
    public static void initDefault(Adapter adapter) {
        checkAdapter(adapter);
        getDefault().mAdapter = adapter;
    }

    /**
     * StateViewLayout(loading status view) wrap the whole activity
     * wrapper is android.R.id.content
     *
     * @param activity current activity object
     * @return holder of StateViewLayout
     */
    public Holder wrap(Activity activity) {
        ViewGroup wrapper = activity.findViewById(android.R.id.content);
        return wrap(wrapper.getChildAt(0));
//        return new Holder(mAdapter, activity, wrapper);
    }

    /**
     * StateViewLayout(loading status view) wrap the specific view.
     *
     * @param view view to be wrapped
     * @return Holder
     */
    public Holder wrap(View view) {
        FrameLayout wrapper = new FrameLayout(view.getContext());
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null) {
            wrapper.setLayoutParams(lp);
        }
        if (view.getParent() != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            int index = parent.indexOfChild(view);
            parent.removeView(view);
            parent.addView(wrapper, index);
        }
        LayoutParams newLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        wrapper.addView(view, newLp);
        return new Holder(mAdapter, view.getContext(), wrapper);
    }

    /**
     * 检查是否设置了自定义adapter，必须先初始化
     *
     * @param adapter adapter
     */
    private static void checkAdapter(Adapter adapter) {
        if (adapter == null) {
            throw new NullPointerException("please set initDefault at first");
        }
    }

    /**
     * StateViewLayout holder<br>
     * create by {@link StatusHolder#wrap(Activity)} or {@link StatusHolder#wrap(View)}<br>
     * the core API for showing all status view
     */
    public static class Holder {

        private Adapter mAdapter;
        private Context mContext;
        private Runnable mRetryTask;
        private View mCurStatusView;
        private ViewGroup mWrapper;
        private LoadStatus curState = LoadStatus.SUCCESS;
        private Object mData;

        public LoadStatus getCurState() {
            return curState;
        }

        private Holder(Adapter adapter, Context context, ViewGroup wrapper) {
            this.mAdapter = adapter;
            this.mContext = context;
            this.mWrapper = wrapper;
        }

        /**
         * set retry task when user click the retry button in load failed page
         *
         * @param task when user click in load failed UI, run this task
         * @return this
         */
        public Holder withRetry(Runnable task) {
            mRetryTask = task;
            return this;
        }

        /**
         * set extension data
         *
         * @param data extension data
         * @return this
         */
        public Holder withData(Object data) {
            this.mData = data;
            return this;
        }

        public void showLoading() {
            showLoadingStatus(LoadStatus.LOADING);
        }

        public void showLoadSuccess() {
            showLoadingStatus(LoadStatus.SUCCESS);
        }

        public void showLoadFailed() {
            showLoadingStatus(LoadStatus.ERROR);
        }

        public void showEmpty() {
            showLoadingStatus(LoadStatus.EMPTY);
        }

        /**
         * Show specific status UI
         *
         * @param status status
         * @see #showLoading()
         * @see #showLoadFailed()
         * @see #showLoadSuccess()
         * @see #showEmpty()
         */
        public void showLoadingStatus(LoadStatus status) {
            if (curState == status || !validate()) {
                return;
            }
            curState = status;
            try {
                //call customer adapter to get UI for specific status. convertView can be reused
                View view = mAdapter.getView(this, mCurStatusView, status);
                if (view == null) {
                    printLog(mAdapter.getClass().getName() + ".getView returns null");
                    return;
                }
                if (view != mCurStatusView || mWrapper.indexOfChild(view) < 0) {
                    if (mCurStatusView != null) {
                        mWrapper.removeView(mCurStatusView);
                    }
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        view.setElevation(Float.MAX_VALUE);
//                    }
                    mWrapper.addView(view);
                    ViewGroup.LayoutParams lp = view.getLayoutParams();
                    if (lp != null) {
                        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    }
                } else if (mWrapper.indexOfChild(view) != mWrapper.getChildCount() - 1) {
                    // 确保加载状态视图在前面
                    view.bringToFront();
                }
                mCurStatusView = view;
            } catch (Exception e) {
                if (DEBUG) {
                    e.printStackTrace();
                }
            }
        }

        private boolean validate() {
            if (mAdapter == null) {
                printLog("StateViewLayout.Adapter is not specified.");
            }
            if (mContext == null) {
                printLog("Context is null.");
            }
            if (mWrapper == null) {
                printLog("The mWrapper of loading status view is null.");
            }
            return mAdapter != null && mContext != null && mWrapper != null;
        }

        public Context getContext() {
            return mContext;
        }

        /**
         * get wrapper
         *
         * @return container of gloading
         */
        public ViewGroup getWrapper() {
            return mWrapper;
        }

        /**
         * get retry task
         *
         * @return retry task
         */
        public Runnable getRetryTask() {
            return mRetryTask;
        }

        /**
         * get extension data
         *
         * @param <T> return type
         * @return data
         */
        public <T> T getData() {
            try {
                return (T) mData;
            } catch (Exception e) {
                if (DEBUG) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    private static void printLog(String msg) {
        if (DEBUG) {
            Log.e("StateViewLayout", msg);
        }
    }
}
