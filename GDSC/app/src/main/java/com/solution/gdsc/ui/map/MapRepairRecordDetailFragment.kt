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
import com.solution.gdsc.ui.map.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapRepairRecordDetailFragment : BaseFragment<FragmentMapRepairRecordDetailBinding>(R.layout.fragment_map_repair_record_detail) {
    private val args by navArgs<MapRepairRecordDetailFragmentArgs>()
    private val viewModel by viewModels<MapViewModel>()

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
        }
    }

    private fun setRecordDetail() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.repairRecordDetail.collectLatest {
                    if (it.status in 200..299) {
                        binding.repairRecordDto = it.data
                    }
                }
            }
        }
    }
}