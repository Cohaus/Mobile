package com.solution.gdsc.ui.home

import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentHomeUseGuideBinding
import com.solution.gdsc.ui.common.UseGuide
import com.solution.gdsc.ui.home.adapter.UseGuideViewPagerAdapter

class HomeUseGuidFragment : BaseFragment<FragmentHomeUseGuideBinding>(R.layout.fragment_home_use_guide) {
    private val items = listOf(
        UseGuide.ONE,
        UseGuide.TWO,
        UseGuide.THREE,
        UseGuide.FOUR,
        UseGuide.FIVE,
        UseGuide.SIX,
        UseGuide.SEVEN,
        UseGuide.EIGHT,
        UseGuide.NINE,
        UseGuide.TEN
    )

    override fun setLayout() {
        binding.viewPagerUseGuide.adapter = UseGuideViewPagerAdapter(this, items)
        TabLayoutMediator(binding.tabLayoutUseGuide, binding.viewPagerUseGuide) { _, _ ->
        }.attach()
        binding.toolbarUseGuide.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}