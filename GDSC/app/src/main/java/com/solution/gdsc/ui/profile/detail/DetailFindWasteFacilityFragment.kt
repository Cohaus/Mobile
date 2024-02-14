package com.solution.gdsc.ui.profile.detail

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentDetailFindWasteFacilityBinding
import com.solution.gdsc.ui.map.viewmodel.MapViewModel
import com.solution.gdsc.ui.profile.adapter.WasteFacilityAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFindWasteFacilityFragment : BaseFragment<FragmentDetailFindWasteFacilityBinding>(R.layout.fragment_detail_find_waste_facility) {
    private val args by navArgs<DetailFindWasteFacilityFragmentArgs>()
    private val viewModel by viewModels<MapViewModel>()
    private val adapter = WasteFacilityAdapter()

    override fun setLayout() {
        viewModel.getWasteFacilityInfo(args.repairId)
        setAdapter()
        binding.toolbarWasteFacility.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setAdapter() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.wasteFacilityInfo.collectLatest {
                    if (it.data != null) {
                        Log.e("TAG", it.data.toString())
                        adapter.add(it.data.wasteFacilities.content)
                        binding.rvWasteFacilityList.adapter = adapter
                        changeVisibility(adapter.itemCount)
                    } else {
                        binding.tvEmptyWasteFacility.visibility = View.VISIBLE
                        binding.groupSuccessFindWasteFacility.visibility  = View.GONE
                    }
                }
            }
        }
    }

    private fun changeVisibility(count: Int) {
        with(binding) {
            if (count > 0) {
                groupSuccessFindWasteFacility.visibility = View.VISIBLE
                tvEmptyWasteFacility.visibility = View.GONE
            } else {
                groupSuccessFindWasteFacility.visibility = View.GONE
                tvEmptyWasteFacility.visibility = View.VISIBLE
            }
        }
    }
}