package com.solution.gdsc.ui.map

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentMapRepairRecordDetailBinding
import com.solution.gdsc.domain.model.response.RepairRecordDto
import com.solution.gdsc.ui.common.AiGrade
import com.solution.gdsc.ui.map.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapRepairRecordDetailFragment : BaseFragment<FragmentMapRepairRecordDetailBinding>(R.layout.fragment_map_repair_record_detail) {
    private val args by navArgs<MapRepairRecordDetailFragmentArgs>()
    private val viewModel by viewModels<MapViewModel>()
    private var repairRecord: RepairRecordDto? = null

    override fun setLayout() {
        viewModel.getRepairRecordDetail(args.repairId)
        setRecordDetail()
        setClickListener()
    }

    private fun setClickListener() {
        with(binding) {
            tvMapRepairApply.setOnClickListener {
                val action = MapRepairRecordDetailFragmentDirections
                    .actionMapNavigationRepairRecordDetailToMapRepairInfoFragment(args.repairId, binding.tvMapRepairTitle.text.toString())
                findNavController().navigate(action)
            }
            ibMapRepairBackButton.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setRecordDetail() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.repairRecordDetail.collectLatest {
                    if (it.status in 200..299) {
                        repairRecord = it.data
                        binding.repairRecordDto = it.data
                        setProgressbarColor()
                    }
                }
            }
        }
    }

    private fun setProgressbarColor() {
        with(binding) {
            when (repairRecord!!.grade) {
                AiGrade.SUPERIORITY.grade -> progressBarMapSafeGrade.setProgressBarColor(R.color.green_300)
                AiGrade.GENERAL.grade -> progressBarMapSafeGrade.setProgressBarColor(R.color.green_300)
                AiGrade.FAULTY.grade -> progressBarMapSafeGrade.setProgressBarColor(R.color.red)
            }
        }
    }
}