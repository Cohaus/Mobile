package com.solution.gdsc.ui.profile

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentPostRepairApplyInfoBinding
import com.solution.gdsc.ui.common.RepairStatus
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostRepairApplyInfoFragment : BaseFragment<FragmentPostRepairApplyInfoBinding>(R.layout.fragment_post_repair_apply_info) {
    private val viewModel by viewModels<ProfileViewModel>()
    private val args by navArgs<PostRepairApplyInfoFragmentArgs>()

    override fun setLayout() {
        viewModel.getRepairInfo(args.repairId)
        setRepairInfo()
        binding.toolbarRepairApplyInfo.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.progressBarRepairInfo.progress = 33
    }

    private fun setRepairInfo() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.repairInfo.collectLatest {
                    if (it.status in 200..299) {
                        binding.repairInfo = it.data!!
                        setRepairInfoVisibility(it.data.repairStatus)
                    }
                }
            }
        }
    }

    private fun setRepairInfoVisibility(status: String) {
        with(binding) {
            if (status == RepairStatus.REQUEST.type) {
                groupFindBefore.visibility = View.VISIBLE
                groupFindComplete.visibility = View.GONE
            } else {
                groupFindBefore.visibility = View.GONE
                groupFindComplete.visibility = View.VISIBLE
            }
        }
    }
}