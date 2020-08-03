package com.dsy.mvp.ui.fragment

import android.os.Bundle
import com.dsy.mvp.R
import com.dsy.mvp.base.BaseFragment
import com.dsy.mvp.base.SimplePresenter

class SimpleFragment : BaseFragment<SimplePresenter>(R.layout.fragment_simple) {

    override fun initView() {}
    override fun initEvent() {}
    override fun initData() {}

    companion object {
        fun newInstance(): SimpleFragment {
            val fragment = SimpleFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}