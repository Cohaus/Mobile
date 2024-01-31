package com.solution.gdsc.ui.profile.setting

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentProfileSettingBinding
import com.solution.gdsc.ui.common.DialogCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileSettingFragment : BaseFragment<FragmentProfileSettingBinding>(R.layout.fragment_profile_setting) {
    private val args by navArgs<ProfileSettingFragmentArgs>()

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
                val action = ProfileSettingFragmentDirections.actionProfileSettingToProfileInfoModify(args.userInfo)
                findNavController().navigate(action)
            }
            tvVolunteerRegistration.setOnClickListener {
                val action = ProfileSettingFragmentDirections.actionProfileSettingToSettingVolunteerRegistration()
                findNavController().navigate(action)
            }
        }
    }
}