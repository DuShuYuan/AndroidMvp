package com.dsy.mvp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dsy.mvp.R
import com.dylanc.loadinghelper.LoadingHelper

/**
 * ❌错误状态布局
 */
class ErrorAdapter : LoadingHelper.Adapter<ErrorAdapter.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.layout_error, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder) {
        holder.btnReload.setOnClickListener {
            holder.onReloadListener?.onReload()
        }
    }

    class ViewHolder internal constructor(rootView: View) : LoadingHelper.ViewHolder(rootView) {
        val btnReload: View = rootView.findViewById(R.id.btn_reload)

    }
}