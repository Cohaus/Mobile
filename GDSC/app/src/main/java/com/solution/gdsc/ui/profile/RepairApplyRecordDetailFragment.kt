package com.solution.gdsc.ui.profile

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentRepairApplyRecordDetailBinding
import com.solution.gdsc.domain.model.response.RepairRecordDto
import com.solution.gdsc.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepairApplyRecordDetailFragment : BaseFragment<FragmentRepairApplyRecordDetailBinding>(R.layout.fragment_repair_apply_record_detail) {
    private val viewModel by viewModels<ProfileViewModel>()
    private val args by navArgs<RepairApplyRecordDetailFragmentArgs>()
    private lateinit var repairRecord: RepairRecordDto

    override fun setLayout() {
        Log.e("ARGS", args.repairID.toString())
        viewModel.getRepairsRecord(args.repairID)
        setInfo()
        setClickListener()
    }

    private fun setInfo() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.repairRecord.collectLatest {
                    if (it.status == 200) {
                        repairRecord = it.data!!
                        binding.repairRecordDto = repairRecord
                    }
                }
            }
        }
    }

    private fun setClickListener() {
        with(binding) {
            ibRepairBackButton.setOnClickListener {
                findNavController().navigateUp()
            }
            ibRepairMoreButton.setOnClickListener {
                val action =
                    RepairApplyRecordDetailFragmentDirections
                        .actionRepairApplyRecordDetailToRepairDetailMore(repairRecord.recordId)
                findNavController().navigate(action)
            }
        }
    }
}