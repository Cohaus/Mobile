package com.solution.gdsc.ui.profile

import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentPostRepairApplyInfoBinding

class PostRepairApplyInfoFragment : BaseFragment<FragmentPostRepairApplyInfoBinding>(R.layout.fragment_post_repair_apply_info) {
    override fun setLayout() {
        binding.toolbarRepairApplyInfo.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}