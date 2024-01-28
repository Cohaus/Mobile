package com.solution.gdsc.ui.home

import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentHomeRepairApplyBinding

class HomeRepairApplyFragment : BaseFragment<FragmentHomeRepairApplyBinding>(R.layout.fragment_home_repair_apply) {
    override fun setLayout() {
        binding.btnRepairApplySave.setOnClickListener {
            val action = HomeRepairApplyFragmentDirections.actionHomeRepairApplyToHome()
            findNavController().navigate(action)
        }
        binding.toolbarRepairApply.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}