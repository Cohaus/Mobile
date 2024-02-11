package com.solution.gdsc.ui.profile.viewmodel

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentRepairApplyRecordDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepairApplyRecordDetailFragment : BaseFragment<FragmentRepairApplyRecordDetailBinding>(R.layout.fragment_repair_apply_record_detail) {
    private val viewModel by viewModels<ProfileViewModel>()
    private val args by navArgs<RepairApplyRecordDetailFragmentArgs>()

    override fun setLayout() {
        Log.e("ARGS", args.repairID.toString())
        viewModel.getRepairsRecord(args.repairID)
        setInfo()
    }

    private fun setInfo() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.repairRecord.collectLatest {
                    if (it.status == 200) {
                        Log.e("data", it.data.toString())
                        binding.repairRecordDto = it.data
                    }
                }
            }
        }
    }
}