package com.solution.gdsc.ui.profile

import android.view.View
import androidx.constraintlayout.widget.Group
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
import com.solution.gdsc.ui.profile.adapter.RepairClickListener
import com.solution.gdsc.ui.profile.adapter.VolunteerCompleteAdapter
import com.solution.gdsc.ui.profile.adapter.VolunteerProceedAdapter
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile),
    PostClickListener, RepairClickListener {
    private val viewModel by viewModels<ProfileViewModel>()
    private val repairAdapter = RepairApplyAdapter(this)
    private val saveApter = RecordSaveApter(this)
    private val proceedAdapter = VolunteerProceedAdapter()
    private val completeAdapter = VolunteerCompleteAdapter()
    private var repairList = mutableListOf<RecordItem>()

    override fun setLayout() {
        binding.isLoading = true
        viewModel.getUserRecord()
        viewModel.getUserInfo()
        setToolbarMenu()
        observe()
        initVolunteerProceed()
        initVolunteerComplete()
    }

    private fun setToolbarMenu() {
        binding.toolbarProfile.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.toolbar_ic_setting -> {
                    val action =
                        ProfileFragmentDirections.actionProfileToProfileSetting(binding.userDto!!)
                    findNavController().navigate(action)
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun initVolunteerProceed() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {

            }
        }
    }

    private fun initVolunteerComplete() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {

            }
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.userInfo.collectLatest {
                    withContext(Dispatchers.Main) {
                        binding.userDto = it
                        binding.isLoading = false
                        setProfileImage(it.volunteerType)
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.savedRecords.collectLatest {
                    saveApter.update(it)
                    binding.rvSaveList.adapter = saveApter
                    binding.isLoading = false
                    changeGroupVisibility(saveApter.itemCount, binding.groupSave)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.repairRecords.collectLatest {
                    repairAdapter.update(it)
                    binding.rvRepairApplyList.adapter = repairAdapter
                    binding.isLoading = false
                    changeGroupVisibility(repairAdapter.itemCount, binding.groupRepair)
                }
            }
        }
    }

    private fun setProfileImage(type: String?) {
        with(binding) {
            if (type.isNullOrEmpty()) {
                ivUserStateImage.setImageResource(R.drawable.ic_normal_user)
            } else {
                ivUserStateImage.setImageResource(R.drawable.ic_volunteer_user)
            }
            ivUserStateImage.visibility = View.VISIBLE
        }
    }

    private fun changeGroupVisibility(count: Int, group: Group) {
        if (count > 0) {
            group.visibility = View.VISIBLE
        }
    }

    override fun onPostClick(post: RecordItem) {
        val action = ProfileFragmentDirections.actionProfileToPostDetail(post)
        findNavController().navigate(action)
    }

    override fun onRepairClick(repairId: Long) {
        val action = ProfileFragmentDirections.actionProfileToRepairApplyRecordDetail(repairId)
        findNavController().navigate(action)
    }
}