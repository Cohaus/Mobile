package com.solution.gdsc.ui.profile.setting

import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentProfileInfoModifyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileInfoModifyFragment : BaseFragment<FragmentProfileInfoModifyBinding>(R.layout.fragment_profile_info_modify) {
    private val args by navArgs<ProfileInfoModifyFragmentArgs>()

    override fun setLayout() {
        val userInfo = args.userInfo
        binding.userInfo = userInfo
    }
}