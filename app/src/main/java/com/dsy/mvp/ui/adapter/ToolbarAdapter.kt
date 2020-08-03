package com.dsy.mvp.ui.adapter

import android.app.Activity
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.dsy.mvp.R
import com.dylanc.loadinghelper.LoadingHelper

/**
 * 标题栏布局
 */
class ToolbarAdapter : LoadingHelper.Adapter<ToolbarAdapter.ViewHolder> {
    private var title: String
    private var type: NavIconType
    private var menuId = 0
    private var onMenuItemClick: Function1<MenuItem, Boolean>? = null

    constructor(title: String, type: NavIconType) {
        this.title = title
        this.type = type
    }

    constructor(title: String, type: NavIconType, menuId: Int, onMenuItemClick: Function1<MenuItem, Boolean>?) {
        this.title = title
        this.type = type
        this.menuId = menuId
        this.onMenuItemClick = onMenuItemClick
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.layout_toolbar, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (!TextUtils.isEmpty(title)) {
            holder.toolbar.title = title
        }
        if (type === NavIconType.BACK) {
            holder.toolbar.setNavigationIcon(R.drawable.ic_back)
            holder.toolbar.setNavigationOnClickListener { holder.activity.finish() }
        } else {
            holder.toolbar.navigationIcon = null
        }
        if (menuId > 0 && onMenuItemClick != null) {
            holder.toolbar.inflateMenu(menuId)
            holder.toolbar.setOnMenuItemClickListener {
                onMenuItemClick?.invoke(it)
                true
            }
        }
    }

    class ViewHolder(rootView: View) : LoadingHelper.ViewHolder(rootView) {
        val toolbar: Toolbar = rootView as Toolbar
        val activity = rootView.context as Activity
    }
}