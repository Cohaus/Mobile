package com.solution.gdsc.ui.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentProfileBinding
import com.solution.gdsc.domain.model.response.RecordItem
import com.solution.gdsc.ui.profile.adapter.PostClickListener
import com.solution.gdsc.ui.profile.adapter.RecordSaveApter
import com.solution.gdsc.ui.profile.adapter.RepairApplyAdapter
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile), PostClickListener {
    private val viewModel by viewModels<ProfileViewModel>()
    private val repairAdapter = RepairApplyAdapter(this)
    private val saveApter = RecordSaveApter(this)

    override fun setLayout() {
        viewModel.getUserInfo()
        viewModel.getUserRecord()
        setToolbarMenu()
        observe()
    }

    private fun setToolbarMenu() {
        binding.toolbarProfile.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.toolbar_ic_setting -> {
                    val action = ProfileFragmentDirections.actionProfileToProfileSetting(binding.userDto!!)
                    findNavController().navigate(action)
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun observe() {
        viewModel.userInfo.observe(viewLifecycleOwner) {
            binding.userDto = it
        }
        viewModel.userRecords.observe(viewLifecycleOwner) {
            val result = it
            repairAdapter.add(result.repairRecord.content)
            saveApter.add(result.savedRecord.content)
            binding.rvSaveList.adapter = saveApter
            binding.rvRepairApplyList.adapter = repairAdapter
        }
    }

    override fun onPostClick(post: RecordItem) {
        val action = ProfileFragmentDirections.actionProfileToPostDetail(post)
        findNavController().navigate(action)
    }
}