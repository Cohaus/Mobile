package com.solution.gdsc.ui.profile

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile), PostClickListener {
    private val viewModel by viewModels<ProfileViewModel>()
    private val repairAdapter = RepairApplyAdapter(this)
    private val saveApter = RecordSaveApter(this)
    private var savedList = mutableListOf<RecordItem>()
    private  var repairList = mutableListOf<RecordItem>()

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

    private fun initAdapter() {
        repairAdapter.update(repairList)
        saveApter.update(savedList)
        binding.rvSaveList.adapter = saveApter
        binding.rvRepairApplyList.adapter = repairAdapter
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userInfo.collectLatest {
                    binding.userDto = it
                }
            }
        }
        /*viewModel.userInfo.observe(viewLifecycleOwner) {
            binding.userDto = it
        }*/
        viewModel.userRecords.observe(viewLifecycleOwner) {
            val result = it
            savedList.clear()
            savedList.addAll(result.savedRecord.content)
            repairList.clear()
            repairList.addAll(result.repairRecord.content)
            initAdapter()
        }
    }

    override fun onPostClick(post: RecordItem) {
        val action = ProfileFragmentDirections.actionProfileToPostDetail(post)
        findNavController().navigate(action)
    }
}