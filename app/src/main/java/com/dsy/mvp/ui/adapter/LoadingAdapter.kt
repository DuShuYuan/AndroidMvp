package com.dsy.mvp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dsy.mvp.R
import com.dylanc.loadinghelper.LoadingHelper

/**
 * 加载状态布局
 */
class LoadingAdapter : LoadingHelper.Adapter<LoadingHelper.ViewHolder> {
    private var height = ViewGroup.LayoutParams.MATCH_PARENT

    constructor()

    constructor(height: Int) {
        this.height = height
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): LoadingHelper.ViewHolder {
        return LoadingHelper.ViewHolder(inflater.inflate(R.layout.layout_loading, parent, false))
    }

    override fun onBindViewHolder(holder: LoadingHelper.ViewHolder) {
        val layoutParams = holder.rootView.layoutParams
        layoutParams.height = height
        holder.rootView.layoutParams = layoutParams
    }
}