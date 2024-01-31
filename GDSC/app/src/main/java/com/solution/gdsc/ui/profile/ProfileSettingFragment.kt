package com.solution.gdsc.ui.profile

import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentProfileSettingBinding
import com.solution.gdsc.ui.common.DialogCategory

class ProfileSettingFragment : BaseFragment<FragmentProfileSettingBinding>(R.layout.fragment_profile_setting) {
    override fun setLayout() {
        with(binding) {
            toolbarSetting.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            tvLogout.setOnClickListener {
                val action = ProfileSettingFragmentDirections
                    .actionProfileSettingToSettingStateDialog(DialogCategory.LOGOUT)
                findNavController().navigate(action)
            }
            tvWithdrawal.setOnClickListener {
                val action = ProfileSettingFragmentDirections
                    .actionProfileSettingToSettingStateDialog(DialogCategory.WITHDRAWAL)
                findNavController().navigate(action)
            }
            tvSettingInfoModify.setOnClickListener {
                val action = ProfileSettingFragmentDirections.actionProfileSettingToProfileInfoModify()
                findNavController().navigate(action)
            }
        }
    }
}