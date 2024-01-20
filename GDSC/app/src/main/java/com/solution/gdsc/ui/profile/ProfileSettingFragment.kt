package com.solution.gdsc.ui.profile

import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentProfileSettingBinding

class ProfileSettingFragment : BaseFragment<FragmentProfileSettingBinding>(R.layout.fragment_profile_setting) {
    override fun setLayout() {
        with(binding) {
            toolbarSetting.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            tvLogout.setOnClickListener {
                val action = ProfileSettingFragmentDirections.actionProfileSettingToLogoutDialog()
                findNavController().navigate(action)
            }
        }
    }
}