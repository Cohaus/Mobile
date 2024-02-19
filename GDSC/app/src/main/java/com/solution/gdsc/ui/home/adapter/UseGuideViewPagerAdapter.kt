package com.solution.gdsc.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.solution.gdsc.ui.common.UseGuide
import com.solution.gdsc.ui.home.HomeUseGuidItemFragment

class UseGuideViewPagerAdapter(
    fragment: Fragment,
    private val items: List<UseGuide>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return HomeUseGuidItemFragment.newInstance(items[position])
    }
}