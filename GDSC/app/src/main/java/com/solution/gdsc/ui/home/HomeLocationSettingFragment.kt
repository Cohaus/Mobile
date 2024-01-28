package com.solution.gdsc.ui.home

import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentHomeLocationSettingBinding

class HomeLocationSettingFragment : BaseFragment<FragmentHomeLocationSettingBinding>(R.layout.fragment_home_location_setting) {
    override fun setLayout() {
        binding.btnLocationSettingNext.setOnClickListener {
            val action =
                HomeLocationSettingFragmentDirections.actionHomeLocationSettingToHomeRepairApply()
            findNavController().navigate(action)
        }
    }
}