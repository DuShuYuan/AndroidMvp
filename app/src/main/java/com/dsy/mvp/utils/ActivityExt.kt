package com.dsy.mvp.utils

import android.app.Activity
import com.dsy.mvp.ui.adapter.NavIconType
import com.dsy.mvp.ui.adapter.ToolbarAdapter
import com.dylanc.loadinghelper.LoadingHelper
import com.dylanc.loadinghelper.ViewType

fun Activity.setToolbar(title: String, type: NavIconType = NavIconType.NONE) =
        LoadingHelper(this).apply {
            register(ViewType.TITLE, ToolbarAdapter(title, type))
            setDecorHeader(ViewType.TITLE)
        }
