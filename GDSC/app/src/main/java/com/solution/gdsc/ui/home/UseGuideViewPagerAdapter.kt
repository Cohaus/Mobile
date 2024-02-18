package com.solution.gdsc.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class UseGuideViewPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 0
    }

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }
}