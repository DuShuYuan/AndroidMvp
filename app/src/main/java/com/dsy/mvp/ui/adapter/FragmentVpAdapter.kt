package com.dsy.mvp.ui.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dsy.mvp.utils.MLog

/**
 * @Description: viewpager adapter
 * @Author: DuShuYuan
 * @Date: 2020-08-03 11:07:55
 */
class FragmentVpAdapter(
        fm: FragmentManager,
        private val fragments: List<Fragment>,
        isResume: Boolean = true
) : FragmentPagerAdapter(fm, if (isResume) BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT else BEHAVIOR_SET_USER_VISIBLE_HINT) {
    override fun getItem(fragment: Int): Fragment {
        return fragments[fragment]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun finishUpdate(container: ViewGroup) {
        try {
            super.finishUpdate(container)
        } catch (nullPointerException: NullPointerException) {
            MLog.d("Catch the NullPointerException in FragmentPagerAdapter.finishUpdate")
        }
    }

}