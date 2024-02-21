package com.solution.gdsc.ui.map

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentMapRepairInfoBinding
import com.solution.gdsc.domain.model.response.RepairInfoDto
import com.solution.gdsc.ui.common.RepairStatus
import com.solution.gdsc.ui.map.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapRepairInfoFragment : BaseFragment<FragmentMapRepairInfoBinding>(R.layout.fragment_map_repair_info) {
    private val args by navArgs<MapRepairInfoFragmentArgs>()
    private val viewModel by viewModels<MapViewModel>()
    private lateinit var repairInfo: RepairInfoDto

    override fun setLayout() {
        viewModel.getRepairInfo(args.repairId)
        setRepairInfo()
        setClickListener()
    }

    private fun setRepairInfo() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.repairInfo.collectLatest {
                    if (it.status in 200..299) {
                        repairInfo = it.data!!
                        binding.repairInfo = repairInfo
                        changeVisibility()
                        setEcoWasteDialog(repairInfo.repairStatus)
                    }
                }
            }
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.repairInfo.collectLatest {
                    if (it.status in 200..299) {
                        repairInfo = it.data!!
                        binding.repairInfo = repairInfo
                        changeVisibility()
                    }
                }
            }
        }
    }

    private fun setEcoWasteDialog(repairStatus: String) {
        if (repairStatus == RepairStatus.COMPLETE.type) {
            binding.tvVolunteerRepairComplete.visibility = View.GONE
            val action = MapRepairInfoFragmentDirections.actionMapRepairInfoToDialogEcoWaste(args.repairId)
            findNavController().navigate(action)
        }
    }

    private fun setClickListener() {
        with(binding) {
            btnVolunteerApplyButton.setOnClickListener {
                val action = MapRepairInfoFragmentDirections.actionMapRepairInfoToMapRepairApply(
                    repairInfo!!.date,
                    args.title,
                    args.repairId
                )
                findNavController().navigate(action)
            }
            toolbarRepairApplyInfo.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            tvVolunteerRepairComplete.setOnClickListener {
                val action = MapRepairInfoFragmentDirections.actionMapRepairInfoToDialogRepairComplete(args.repairId, repairInfo!!.date)
                findNavController().navigate(action)
            }
        }
    }

    private fun changeVisibility() {
        with(binding) {
            if (repairInfo!!.volunteerName == null) {
                groupMatchBefore.visibility = View.VISIBLE
                groupMatchAfter.visibility = View.GONE
                groupRepairProceed.visibility = View.GONE
            } else {
                groupMatchBefore.visibility = View.GONE
                groupMatchAfter.visibility = View.VISIBLE
                groupRepairProceed.visibility = View.VISIBLE
                changeRepairStartGroup()
            }
        }
    }

    private fun changeRepairStartGroup() {
        with(binding) {
            tvRepairApplyDateBox.setTextColor(resources.getColor(R.color.gray_450))
            tvRepairApplyDateBox.setBackgroundResource(R.drawable.shape_button_24dp_gray400)
            imageView2.setImageResource(R.drawable.ic_calendar_gray)
        }
    }
}