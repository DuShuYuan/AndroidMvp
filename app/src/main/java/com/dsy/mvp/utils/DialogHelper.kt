package com.dsy.mvp.utils

import android.content.Context
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener

/**
 * @Description: 弹窗
 * @Author: DuShuYuan
 * @Date: 2020-08-13 18:40:47
 */
class DialogHelper(private val context: Context, private val config: DialogHelper.() -> Unit) {
    var title: String? = null
    var content: String = ""
    var confirmTxt: String = "确认"
    var cancelTxt: String = "取消"
    var isHideCancel: Boolean = false
    var onConfirmClick: (() -> Unit)? = null
    var onCancelClick: (() -> Unit)? = null
    private var cancelListener: OnCancelListener? = null
    private var confirmListener: OnConfirmListener? = null

    /**
     * 显示弹窗
     */
    fun show() {
        config.invoke(this)
        onCancelClick?.let {
            cancelListener = OnCancelListener {
                it.invoke()
            }
        }
        onConfirmClick?.let {
            confirmListener = OnConfirmListener {
                it.invoke()
            }
        }
        XPopup.Builder(context)
                .asConfirm(title, content, cancelTxt, confirmTxt, confirmListener, cancelListener, isHideCancel, 0)
                .show()
    }
}