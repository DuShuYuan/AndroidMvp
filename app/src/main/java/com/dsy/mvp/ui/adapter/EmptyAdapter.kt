package com.dsy.mvp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dsy.mvp.R
import com.dylanc.loadinghelper.LoadingHelper

/**
 * 空状态布局
 */
class EmptyAdapter : LoadingHelper.Adapter<LoadingHelper.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): LoadingHelper.ViewHolder {
        return LoadingHelper.ViewHolder(inflater.inflate(R.layout.layout_empty, parent, false))
    }

    override fun onBindViewHolder(holder: LoadingHelper.ViewHolder) {}
}